package main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.entity.synchronization;

import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.entity.Player;
import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.controller.SynchronizeManager;

/**
 * Entity Synchronized Pong implementing Player
 */
public class Pong implements Player {

    private final SynchronizeManager syncManager;
    private final int MAX_ITERATIONS;

    public Pong(SynchronizeManager syncManager, int maxIterations) {
        this.syncManager = syncManager;
        MAX_ITERATIONS = maxIterations;
    }

    @Override
    public void play() {
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            try {
                syncManager.await(false);
                System.out.println("Pong");
                syncManager.signal(false);
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
