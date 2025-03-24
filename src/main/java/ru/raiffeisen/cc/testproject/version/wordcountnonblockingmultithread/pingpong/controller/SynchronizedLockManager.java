package main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.controller;

/**
 * Lock management by synchronization on common Object and using flag to order threads in turn
 */
public class SynchronizedLockManager implements LockManager {

    private final Object lock = new Object();
    private boolean pingTurn = true;

    @Override
    public void waitForTurn(boolean isPingTurn) {
        synchronized (lock) {
            while (pingTurn != isPingTurn) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Override
    public void switchTurn(boolean isPingTurn) {
        synchronized (lock) {
            pingTurn = isPingTurn;
            lock.notifyAll();
        }
    }
}
