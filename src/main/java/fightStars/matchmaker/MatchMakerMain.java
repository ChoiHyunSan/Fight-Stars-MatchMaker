package fightStars.matchmaker;

import static fightStars.matchmaker.config.MatchMakerConfig.*;

import fightStars.matchmaker.worker.MatchPoolWorker;

public class MatchMakerMain {
    public static void main(String[] args) {
        for (int i = 0; i < WORKER_COUNT; i++) {
            new Thread(new MatchPoolWorker(MODE, TEAM_SIZE), "worker-"+i).start();
        }
    }
}
