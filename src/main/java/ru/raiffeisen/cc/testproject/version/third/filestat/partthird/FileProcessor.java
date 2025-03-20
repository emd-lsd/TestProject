package main.java.ru.raiffeisen.cc.testproject.version.third.filestat.partthird;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Класс управления потоками для обработки файлов
 * Так же в одном потоке собирает суммарное количество слов из очереди,
 * и суммарную статистику из очереди статистик
 *
 * Используется реализация неблокирующей очереди ConcurrentLinkedQueue
 */
public class FileProcessor {
    private final Path inputDir;
    private final Queue<Integer> countsQueue = new ConcurrentLinkedQueue<>();
    private final Queue<FileWordStats> fileWordStats = new ConcurrentLinkedQueue<>();
    private final Queue<Map<String, Integer>> globalWordStats = new ConcurrentLinkedQueue<>();


    public FileProcessor(Path inputDir) {
        this.inputDir = inputDir;
    }

    public void processFiles() {
        try {
            List<Path> files = Files.list(inputDir).filter(Files::isRegularFile).toList();

            List<Thread> threads = files.stream().map(file -> new Thread(new WordCounterTask(file, countsQueue, fileWordStats, globalWordStats))).toList();
            threads.forEach(Thread::start);
            for(Thread thread : threads) {
                thread.join();
            }

            fileWordStats.forEach(FileWordStats::printWordCount); // Вывод количества слов каждого файла

            System.out.println();
            fileWordStats.forEach(FileWordStats::printWordFrequency); // Вывод статистики каждого файла

            int totalCount = countsQueue.stream().mapToInt(Integer::intValue).sum(); // сбор количества слов от всех файлов
            System.out.printf("Общее количество слов во всех файла: %d%n", totalCount);

            // Сбор общей статистики из очереди статистик
            Map<String, Integer> globalStats = new HashMap<>();
            for (Map<String, Integer> stat : globalWordStats){
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
