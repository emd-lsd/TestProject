package main.java.ru.raiffeisen.cc.testproject.wordcountnonblockingmultithread.wordstatistics;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Task for multithreaded counting of statistics of words in files
 * To store the word count results from each file, each task is passed a ConcurrentLinkedQueue,
 * which is an implementation of a thread-safe, non-blocking queue.
 * To store the statistics for each file, the same queue of maps containing file-specific statistics is used.
 * For convenient storage of file information wrappers,
 * there is also a non-blocking queue consisting of file wrappers, each containing the word count and local statistics.
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
