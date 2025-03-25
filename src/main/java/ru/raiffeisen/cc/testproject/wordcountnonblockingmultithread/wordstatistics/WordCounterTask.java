package main.java.ru.raiffeisen.cc.testproject.wordcountnonblockingmultithread.wordstatistics;

import main.java.ru.raiffeisen.cc.testproject.wordcountnonblockingmultithread.service.WordCounterService;
import main.java.ru.raiffeisen.cc.testproject.wordcountnonblockingmultithread.service.WordFrequencyService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Queue;

/**
 * Класс задачи потока для обработки каждого файла
 */
public class WordCounterTask implements Runnable {
    private final Path filePath;
    private final Queue<Integer> countsQueue;
    private final Queue<FileWordStats> fileWordStats;
    private final Queue<Map<String, Integer>> globalWordStats;

    public WordCounterTask(
            Path filePath, Queue<Integer> countsQueue,
            Queue<FileWordStats> fileWordStats,
            Queue<Map<String, Integer>> globalWordStats
    ) {
        this.filePath = filePath;
        this.countsQueue = countsQueue;
        this.fileWordStats = fileWordStats;
        this.globalWordStats = globalWordStats;
    }

    @Override
    public void run() {
        try {
            int wordCount = WordCounterService.countWords(filePath);
            countsQueue.add(wordCount);

            Map<String, Integer> wordFrequency = WordFrequencyService.calculateWordFrequency(filePath); // подсчет статистики для файла

            fileWordStats.add(new FileWordStats(filePath.getFileName().toString(), wordCount, wordFrequency)); // добавление в очередь статистики по файлу

            globalWordStats.add(wordFrequency); //добавляем локальную статистику слов в неблокирующую очередь

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + filePath);
        }
    }
}