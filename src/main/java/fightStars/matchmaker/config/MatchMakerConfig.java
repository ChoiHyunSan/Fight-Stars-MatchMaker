package fightStars.matchmaker.config;

import io.github.cdimascio.dotenv.Dotenv;

public class MatchMakerConfig {

	public static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
	public static final String MODE = dotenv.get("MM_MODE", "Deathmatch");
	public static final int TEAM_SIZE = Integer.parseInt(dotenv.get("MM_TEAM_SIZE", "1"));
	public static final int WORKER_COUNT = Integer.parseInt(dotenv.get("MM_WORKERS", "1"));
	public static final String JWT_SECRET = dotenv.get("MM_JWT_SECRET", "default_secret");
	public static final int EXPIRE_MILL = Integer.parseInt(dotenv.get("MM_EXPIRE_MILL", "300000"));
	public static final String REDIS_URL = dotenv.get("REDIS_URL", "redis://localhost:6379");
	public static final String ROOM_API = dotenv.get("ROOM_API", "http://localhost:8080/room");
}

