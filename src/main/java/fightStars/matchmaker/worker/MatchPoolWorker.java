package fightStars.matchmaker.worker;

import fightStars.matchmaker.dto.MatchInfo;
import fightStars.matchmaker.dto.RoomInfo;
import fightStars.matchmaker.dto.response.RoomCreateResponse;
import fightStars.matchmaker.implement.RoomCreateHelper;
import fightStars.matchmaker.util.JsonUtil;
import fightStars.matchmaker.implement.RedisHelper;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.sync.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MatchPoolWorker implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(MatchPoolWorker.class);

    private final String mode;
    private final int teamSize;

    public MatchPoolWorker(String mode, int teamSize) {
        this.mode = mode;
        this.teamSize = teamSize;
    }

    @Override
    public void run() {
        RedisCommands<String, String> redis = RedisHelper.CMD;
        List<MatchInfo> bucket = new ArrayList<>();
        String queueKey = "match:queue:" + mode;

        while (true) {
            // BLPOP을 통해 Mode에 해당하는 매칭 정보를 꺼내어 확인
            var kv = redis.blpop(Duration.ofSeconds(2).toSeconds(), queueKey);
            String json = kv == null ? null : kv.getValue();
            if (json != null)
                bucket.add(JsonUtil.fromJson(json, MatchInfo.class));

            if (bucket.size() < teamSize) continue;

            // 1) in_match 검사를 통해 매칭할 유저들이 아직 매칭을 대기중인지 확인
            List<String> keys = bucket.stream()
                    .map(r -> "in_match:" + r.userId())
                    .toList();
            boolean allAlive = keys.stream().allMatch(k -> redis.exists(k) == 1);
            if (!allAlive) { requeueValid(bucket, redis, queueKey); bucket.clear(); continue; }

            // 2) Lua 원자 DEL
            String lua = """
                for i = 1, #KEYS do 
                    if redis.call('DEL', KEYS[i]) == 0 then 
                        return 0 
                    end 
                end 
                return 1
            """;
            Long ok = redis.eval(lua, ScriptOutputType.INTEGER,
                    keys.toArray(new String[0]),      // KEYS
                    new String[0]);                   // ARGV(없음)
            if (ok == 0) { requeueValid(bucket, redis, queueKey); bucket.clear(); continue; }

            // 3) 방 생성 요청 후, 방 정보를 유저들이 대기중인 서버들에 전달
            RoomCreateResponse response = RoomCreateHelper.createRoom(bucket);
            if(response == null){ requeueValid(bucket, redis, queueKey); bucket.clear(); continue; }
            for (MatchInfo r : bucket) {
                RoomInfo roomInfo = RoomInfo.create(response, r.userId());
                RedisHelper.publishRoom(r.connectionServerId(), JsonUtil.toJson(roomInfo));
            }
            bucket.clear();
        }
    }

    private void requeueValid(List<MatchInfo> list, RedisCommands<String, String> redis, String queueKey) {
        list.stream()
                .filter(r -> redis.exists("in_match:"+r.userId())==1)
                .forEach(r -> redis.rpush(queueKey, JsonUtil.toJson(r)));
    }
}
