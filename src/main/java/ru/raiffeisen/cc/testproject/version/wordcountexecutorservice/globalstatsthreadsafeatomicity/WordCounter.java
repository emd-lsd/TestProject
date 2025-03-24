package main.java.ru.raiffeisen.cc.testproject.version.wordcountexecutorservice.globalstatsthreadsafeatomicity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class WordCounter implements Runnable {
    private final Path filePath;
    private final AtomicInteger totalWordsCounter;
    private final CommonStorage commonStorage; // общая память

    public WordCounter(Path filePath, AtomicInteger totalWordsCounter, CommonStorage commonStorage) {
        this.filePath = filePath;
        this.totalWordsCounter = totalWordsCounter;
        this.commonStorage = commonStorage;
    }

    @Override
    public void run() {
        try {
            String content = Files.readString(filePath);
            String[] words = content.toLowerCase()
                    .replaceAll("[^a-zа-я0-9\\s]", "")
                    .trim()
                    .split("\\s+");
            int wordCount = words.length;

            // подсчет частоты слов
            Map<String, Integer> wordFrequency = new HashMap<>();
            for (String word : words) {
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
            }

            // Вывод статистики
            StringBuilder result = new StringBuilder();
            result.append(String.format("Файл: %s - %d слов%n", filePath.getFileName(), wordCount));

            wordFrequency.forEach((word, count) ->
                    result.append(String.format(" %s: %d%n", word, count))
            );
            System.out.println(result);

            // Обновление ообщей статистики
            wordFrequency.forEach(commonStorage::addWord);

            totalWordsCounter.addAndGet(wordCount); // добавляем в общий счетчик
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + filePath);
        }
    }
}
