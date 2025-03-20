package main.java.ru.raiffeisen.cc.testproject.version.third.filestat.partfirst;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Класс управления потоками для обработки файлов
 */
public class FileProcessor {
    private final Path inputDir;
    private final Queue<Integer> countsQueue = new ConcurrentLinkedQueue<>();

    public FileProcessor(Path inputDir) {
        this.inputDir = inputDir;
    }

    public void processFiles() {
        try {
            List<Path> files = Files.list(inputDir).filter(Files::isRegularFile).toList();

            List<Thread> threads = files.stream().map(file -> new Thread(new WordCounterTask(file, countsQueue))).toList();
            threads.forEach(Thread::start);
            for(Thread thread : threads) {
                thread.join();
            }

            int totalCount = countsQueue.stream().mapToInt(Integer::intValue).sum();
            System.out.printf("Общее количество слов во всех файла: %d%n", totalCount);
        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка обработки файлов: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
