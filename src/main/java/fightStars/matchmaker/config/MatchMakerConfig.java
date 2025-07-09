package fightStars.matchmaker.config;

public class MatchMakerConfig {
	public static final String MODE = System.getenv().getOrDefault("MM_MODE", "Deathmatch");
	public static final int TEAM_SIZE = Integer.parseInt(System.getenv().getOrDefault("MM_TEAM_SIZE", "1"));
	public static final int WORKER_COUNT = Integer.parseInt(System.getenv().getOrDefault("MM_WORKERS", "1"));
}
