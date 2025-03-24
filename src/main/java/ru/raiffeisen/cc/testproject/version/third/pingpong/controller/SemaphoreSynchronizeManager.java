package main.java.ru.raiffeisen.cc.testproject.version.third.pingpong.controller;

import java.util.concurrent.Semaphore;

/**
 * Lock management by Semaphore
 */
public class SemaphoreSynchronizeManager implements SynchronizeManager {
    private final Semaphore pingSemaphore = new Semaphore(1);
    private final Semaphore pongSemaphore = new Semaphore(0);

    @Override
    public void await(boolean isPing) throws InterruptedException {
        if (isPing) {
            pingSemaphore.acquire();
        } else {
            pongSemaphore.acquire();
        }
    }

    @Override
    public void signal(boolean isPing) {
        if (isPing) {
            pongSemaphore.release();
        } else {
            pingSemaphore.release();
        }
    }

    @Override
    public void begin() {
    }
}
