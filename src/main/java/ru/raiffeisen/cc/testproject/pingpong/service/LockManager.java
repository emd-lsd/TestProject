package main.java.ru.raiffeisen.cc.testproject.pingpong.service;

/**
 * Interface for locks
 */
public interface LockManager {
    void waitForTurn(boolean isPingTurn);

    void switchTurn(boolean isPingTurn);
}
