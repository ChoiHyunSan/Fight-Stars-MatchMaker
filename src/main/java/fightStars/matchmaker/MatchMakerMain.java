package fightStars.matchmaker;

import fightStars.matchmaker.worker.MatchPoolWorker;

public class MatchMakerMain {
    public static void main(String[] args) {
        int workers = Integer.parseInt(System.getenv().getOrDefault("MM_WORKERS", "1"));
        for (int i = 0; i < workers; i++) {
            new Thread(new MatchPoolWorker("Deathmatch", 1, 30), "worker-"+i).start();
        }
    }
}