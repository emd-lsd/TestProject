package main.java.ru.raiffeisen.cc.testproject.version.third.pingpong.controller;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock management by ReentrantLock and using flag to order threads in turn
 */
public class ReentrantLockManager implements LockManager {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition pingCondition = lock.newCondition();
    private final Condition pongCondition = lock.newCondition();
    private boolean pingTurn = true;

    @Override
    public void waitForTurn(boolean isPingTurn) {
        lock.lock();
        while (pingTurn != isPingTurn) {
            try {
                if (isPingTurn) {
                    pingCondition.await();
                } else {
                    pongCondition.await();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }

    @Override
    public void switchTurn(boolean isPingTurn) {
        lock.lock();
        try {
            pingTurn = isPingTurn;
            if (isPingTurn) {
                pingCondition.signal();
            } else {
                pongCondition.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}
