package main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.pingpong.controller;

/**
 * Interface for synchronization management
 */
public interface SynchronizeManager {
    void await(boolean isPing) throws InterruptedException;

    void signal(boolean isPing);

    void begin();
}
