package main.java.ru.raiffeisen.cc.testproject.version.third.filestat.wordstatistics;

import main.java.ru.raiffeisen.cc.testproject.version.third.filestat.controller.ThreadManager;
import main.java.ru.raiffeisen.cc.testproject.version.third.filestat.util.FileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Сlass for file processing with threads
 * It also collects the total number of words and the total statistics from the queue in one thread.
 * <p>
 * Uses the ConcurrentLinkedQueue non-blocking queue implementation
 */
public class WordCountFileProcessor {
    private final Path inputDir;
    private final Queue<Integer> countsQueue = new ConcurrentLinkedQueue<>();
    private final Queue<FileWordStats> fileWordStats = new ConcurrentLinkedQueue<>();
    private final Queue<Map<String, Integer>> globalWordStats = new ConcurrentLinkedQueue<>();


    public WordCountFileProcessor(Path inputDir) {
        this.inputDir = inputDir;
    }

    public void processFiles() {
        try {
            List<Path> files = FileUtil.getFilesFromDirectory(inputDir); // get list of Paths
            ThreadManager.processFiles(files, file -> new WordCounterTask(file, countsQueue, fileWordStats, globalWordStats)); // manage threads

            fileWordStats.forEach(FileWordStats::printWordCount); // Output count of words for every file
            System.out.println();
            fileWordStats.forEach(FileWordStats::printWordFrequency); // Output statistics for every file

            int totalCount = countsQueue.stream().mapToInt(Integer::intValue).sum();
            System.out.printf("Общее количество слов во всех файла: %d%n", totalCount);

            Map<String, Integer> globalStats = new HashMap<>();
            for (Map<String, Integer> stat : globalWordStats) {
                stat.forEach((word, count) -> globalStats.merge(word, count, Integer::sum));
            }

            System.out.println("\nСтатистика слов по всем файлам:");
            globalStats.forEach((word, count) -> System.out.printf("  %s: %d%n", word, count));

        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка обработки файлов: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}


// TODO: переименовать этот файл в более понятный отвечающий целям класса
// TODO: вынести создание потоков в приватныйц метод здесь и util для получения списка PATH
// TODO: переименовать пакеты в название задачи и верхние версии пакетов в название логики версий и вынести общие части
// TODO: добавить вызов из другого потока а не из главного потока main
