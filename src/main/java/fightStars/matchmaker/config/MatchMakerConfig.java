package fightStars.matchmaker.config;

public class MatchMakerConfig {
	public static final String MODE = System.getenv().getOrDefault("MM_MODE", "Deathmatch");
	public static final int TEAM_SIZE = Integer.parseInt(System.getenv().getOrDefault("MM_TEAM_SIZE", "1"));
	public static final int WORKER_COUNT = Integer.parseInt(System.getenv().getOrDefault("MM_WORKERS", "1"));
	public static final String JWT_SECRET = System.getenv().getOrDefault("MM_JWT_SECRET", "qwlkfduiwqhrfkeunfsdukfasfzjbcxffeqkufhqerkuhdkfzushfasdfsfqetqet");
	public static final int EXPIRE_MILL = System.getenv().getOrDefault("MM_EXPIRE_MILL", "300000").equals("0") ? 0 : Integer.parseInt(System.getenv().getOrDefault("MM_EXPIRE_MILL", "300000"));
}

