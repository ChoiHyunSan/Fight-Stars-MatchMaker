package fightStars.matchmaker.worker;

import fightStars.matchmaker.dto.MatchRequest;
import fightStars.matchmaker.dto.RoomInfo;
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
    private final int ttlSec;

    public MatchPoolWorker(String mode, int teamSize, int ttlSec) {
        this.mode = mode;
        this.teamSize = teamSize;
        this.ttlSec = ttlSec;
    }

    @Override
    public void run() {
        RedisCommands<String, String> redis = RedisHelper.CMD;
        List<MatchRequest> bucket = new ArrayList<>();
        String queueKey = "match:queue:" + mode;

        while (true) {
            // Blocking Pop 결과 처리
            var kv = redis.blpop(Duration.ofSeconds(2).toSeconds(), queueKey);
            String json = kv == null ? null : kv.getValue();
            if (json != null)
                bucket.add(JsonUtil.fromJson(json, MatchRequest.class));

            if (bucket.size() < teamSize) continue;

            // 1) in_match 검사
            List<String> keys = bucket.stream()
                    .map(r -> "in_match:" + r.userId())
                    .toList();
            boolean allAlive = keys.stream().allMatch(k -> redis.exists(k) == 1);
            if (!allAlive) { requeueValid(bucket, redis, queueKey); bucket.clear(); continue; }

            // 2) Lua 원자 DEL
            String lua = "for i=1,#KEYS do if redis.call('DEL', KEYS[i])==0 then return 0 end end return 1";
            Long ok = redis.eval(lua, ScriptOutputType.INTEGER,
                    keys.toArray(new String[0]),      // KEYS
                    new String[0]);                   // ARGV(없음)
            if (ok == 0) { requeueValid(bucket, redis, queueKey); bucket.clear(); continue; }

            // 3) 방 생성·통지
            // RoomInfo room = HttpUtil.createRoom(bucket);
            RoomInfo room = new RoomInfo("ip", 1234, "asdlkgjsdf", 5L);
            for (MatchRequest r : bucket) {
                RoomInfo payload = new RoomInfo(room.ip(), room.port(), room.roomId(), r.userId());
                RedisHelper.publishRoom(r.connectionServerId(), JsonUtil.toJson(payload));
            }
            bucket.clear();
        }
    }

    private void requeueValid(List<MatchRequest> list, RedisCommands<String, String> redis, String queueKey) {
        list.stream()
                .filter(r -> redis.exists("in_match:"+r.userId())==1)
                .forEach(r -> redis.rpush(queueKey, JsonUtil.toJson(r)));
    }
}