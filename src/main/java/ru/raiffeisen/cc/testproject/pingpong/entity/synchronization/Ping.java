package main.java.ru.raiffeisen.cc.testproject.pingpong.entity.synchronization;

import main.java.ru.raiffeisen.cc.testproject.pingpong.entity.Player;
import main.java.ru.raiffeisen.cc.testproject.pingpong.service.SynchronizeManager;

/**
 * Entity Synchronized Ping implementing Player
 */
public class Ping implements Player {

    private final SynchronizeManager syncManager;
    private final int MAX_ITERATIONS;

    public Ping(SynchronizeManager syncManager, int maxIterations) {
        this.syncManager = syncManager;
        MAX_ITERATIONS = maxIterations;
    }

    @Override
    public void play() {
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            try {
                syncManager.await(true);
                System.out.println("Ping");
                syncManager.signal(true);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void run() {
        play();
    }
}
