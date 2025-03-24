package main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.entity.blocking;

import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.entity.Player;
import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.controller.LockManager;

/**
 * Entity Blocking Pong implementing Player
 */
public class Pong implements Player {

    private final LockManager lockManager;
    private final int MAX_ITERATIONS;

    public Pong(LockManager lockManager, int maxIterations) {
        this.lockManager = lockManager;
        MAX_ITERATIONS = maxIterations;
    }

    @Override
    public void play() {
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            lockManager.waitForTurn(false);
            System.out.println("Pong");
            lockManager.switchTurn(true);
        }
    }

    @Override
    public void run() {
        play();
    }
}
