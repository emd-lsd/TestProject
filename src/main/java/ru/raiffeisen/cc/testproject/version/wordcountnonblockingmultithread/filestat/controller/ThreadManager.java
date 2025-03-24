package main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.filestat.controller;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class ThreadManager {
    /**
     * Create, starts and wait for threads to complete
     *
     * @param files       List of files to process
     * @param taskFactory Factory for creating tasks (WordCounterTask).
     */
    public static void processFiles(List<Path> files, Function<Path, Runnable> taskFactory) throws InterruptedException {
        List<Thread> threads = files.stream().map(file -> new Thread(taskFactory.apply(file))).toList(); // Create threads for every file

        threads.forEach(Thread::start); // Start every thread

        for (Thread thread : threads) { // Wait every thread to complete
            thread.join();
        }
    }
}