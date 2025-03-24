package main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.entity.blocking;

import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.entity.Player;
import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.controller.LockManager;

/**
 * Entity Blocking Ping implementing Player
 */
public class Ping implements Player {

    private final LockManager lockManager;
    private final int MAX_ITERATIONS;

    public Ping(LockManager lockManager, int maxIterations) {
        this.lockManager = lockManager;
        MAX_ITERATIONS = maxIterations;
    }

    @Override
    public void play() {
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            lockManager.waitForTurn(true);
            System.out.println("Ping");
            lockManager.switchTurn(false);
        }
    }

    @Override
    public void run() {
        play();
    }
}
