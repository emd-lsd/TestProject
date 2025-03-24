package main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.controller;

/**
 * Lock management by CountDownLatch
 */

import java.util.concurrent.CountDownLatch;

public class CountDownSynchronizeManager implements SynchronizeManager {

    private CountDownLatch pingLatch = new CountDownLatch(1);
    private CountDownLatch pongLatch = new CountDownLatch(1);

    @Override
    public void await(boolean isPing) throws InterruptedException {
        if (isPing) {
            pingLatch.await();
        } else {
            pongLatch.await();
        }
    }

    @Override
    public void signal(boolean isPing) {
        if (isPing) {
            pongLatch.countDown();
            pingLatch = new CountDownLatch(1);
        } else {
            pingLatch.countDown();
            pongLatch = new CountDownLatch(1);
        }
    }

    @Override
    public void begin() {
        pingLatch.countDown();
    }
}
