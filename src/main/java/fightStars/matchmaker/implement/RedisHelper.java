package fightStars.matchmaker.implement;

import fightStars.matchmaker.config.MatchMakerConfig;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisHelper {

    public static final RedisClient CLIENT = RedisClient.create(MatchMakerConfig.REDIS_URL);
    public static final RedisCommands<String, String> CMD = CLIENT.connect().sync();

    public static void publishRoom(String serverId, String roomInfo) {
        String channel = "conn:" + serverId;
        CMD.publish(channel, roomInfo);
    }
}
