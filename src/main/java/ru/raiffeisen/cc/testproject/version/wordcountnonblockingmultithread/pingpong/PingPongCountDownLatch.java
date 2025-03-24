package main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong;

import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.controller.CountDownSynchronizeManager;
import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.controller.SynchronizeManager;
import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.entity.Player;
import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.entity.synchronization.Ping;
import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.entity.synchronization.Pong;

/**
 * Switching using CountDownLatch by 2 counters
 */
public class PingPongCountDownLatch {
    public static void main(String[] args) {
        int MAX_ITERATIONS = 15;
        SynchronizeManager syncManager = new CountDownSynchronizeManager();
        syncManager.begin();

        Player ping = new Ping(syncManager, MAX_ITERATIONS);
        Player pong = new Pong(syncManager, MAX_ITERATIONS);

        Thread pingThread = new Thread(ping);
        Thread pongThread = new Thread(pong);

        pingThread.start();
        pongThread.start();

    }
}
