package main.java.ru.raiffeisen.cc.testproject.wordcountwiththreads.globalstats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Counter task for multithreaded counting words in files
 */
public class WordCounter implements Runnable {
    private final Path filePath;

    public WordCounter(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            String content = Files.readString(filePath);
            String[] words = content.toLowerCase().replaceAll("[^a-zа-я0-9\\s]", "").trim().split("\\s+");
            int wordCount = words.length;

            Map<String, Integer> localFrequency = new HashMap<>();
            for (String word : words) {
                localFrequency.put(word, localFrequency.getOrDefault(word, 0) + 1);
            }

            // Update global stats
            Main.updateGlobalWordFrequency(localFrequency);

            // Add to global counter
            Main.addToTotalCounter(wordCount);

            // Output local stats
            StringBuilder result = new StringBuilder();
            result.append(String.format("Файл: %s - %d слов%n", filePath.getFileName(), wordCount));
            localFrequency.forEach((word, count) -> result.append(String.format(" %s: %d%n", word, count)));
            System.out.println(result);

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + filePath);
        }
    }
}
