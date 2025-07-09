package fightStars.matchmaker.implement;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisHelper {

    public static final RedisClient CLIENT = RedisClient.create(
            System.getenv().getOrDefault("REDIS_URL", "redis://localhost:6379")
    );

    public static final RedisCommands<String, String> CMD = CLIENT.connect().sync();

    public static void publishRoom(String serverId, String roomInfo) {
        String channel = "conn:" + serverId;
        CMD.publish(channel, roomInfo);
    }
}
