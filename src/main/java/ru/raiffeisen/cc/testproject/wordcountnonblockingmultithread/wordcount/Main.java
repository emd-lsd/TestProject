package main.java.ru.raiffeisen.cc.testproject.wordcountnonblockingmultithread.wordcount;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Exercise about multithreaded word count in files.
 * To store the word count results from each file, each task is passed a ConcurrentLinkedQueue,
 * which is an implementation of a thread-safe, non-blocking queue.
 * <p>
 * Entry point to the program,
 * specifies the directory with files and creates a file processing control processor.
 */
public class Main {
    public static void main(String[] args) {
        Path inputDir = Paths.get("src/main/resources/input");

        Thread fileProcessingThread = new Thread(new FileProcessingThread(inputDir));
        fileProcessingThread.start();

        try {
            fileProcessingThread.join();
        } catch (InterruptedException e) {
            System.err.println("Ожидание потока было прервано: " + e.getMessage());
        }
    }
}
