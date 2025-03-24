package main.java.ru.raiffeisen.cc.testproject.version.third.pingpong.controller;

/**
 * Interface for locks
 */
public interface LockManager {
    void waitForTurn(boolean isPingTurn);

    void switchTurn(boolean isPingTurn);
}
