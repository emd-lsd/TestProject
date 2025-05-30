package main.java.ru.raiffeisen.cc.testproject.wordcountexecutorservice.localstats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Counter task for local calculating word statistics in files
 */
public class WordCounter implements Runnable {
    private final Path filePath;
    private final AtomicInteger totalWordsCounter;

    public WordCounter(Path filePath, AtomicInteger totalWordsCounter) {
        this.filePath = filePath;
        this.totalWordsCounter = totalWordsCounter;
    }

    @Override
    public void run() {
        try {
            String content = Files.readString(filePath);
            String[] words = content.toLowerCase().replaceAll("[^a-zа-я0-9\\s]", "").trim().split("\\s+");
            int wordCount = words.length;

            // Word frequency count
            Map<String, Integer> wordFrequency = new HashMap<>();
            for (String word : words) {
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
            }

            // Output stats
            StringBuilder result = new StringBuilder();
            result.append(String.format("Файл: %s - %d слов%n", filePath.getFileName(), wordCount));

            wordFrequency.forEach((word, count) -> result.append(String.format(" %s: %d%n", word, count)));
            System.out.println(result);

            totalWordsCounter.addAndGet(wordCount); // add to global counter
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + filePath);
        }
    }
}
