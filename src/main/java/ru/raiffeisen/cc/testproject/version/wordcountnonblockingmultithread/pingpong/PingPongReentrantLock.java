package main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong;

import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.controller.LockManager;
import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.controller.ReentrantLockManager;
import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.entity.Player;
import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.entity.blocking.Ping;
import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.entity.blocking.Pong;

/**
 * Switching using ReentrantLock and 2 states and additional check of order via flag
 */
public class PingPongReentrantLock {
    public static void main(String[] args) {
        int MAX_ITERATIONS = 15;
        LockManager lockManager = new ReentrantLockManager();

        Player ping = new Ping(lockManager, MAX_ITERATIONS);
        Player pong = new Pong(lockManager, MAX_ITERATIONS);

        Thread pingThread = new Thread(ping);
        Thread pongThread = new Thread(pong);

        pingThread.start();
        pongThread.start();
    }
}
