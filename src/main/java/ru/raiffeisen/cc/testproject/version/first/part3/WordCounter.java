package main.java.ru.raiffeisen.cc.testproject.version.first.part3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class WordCounter implements Runnable {
    private final Path filePath;
    private final AtomicInteger totalWordsCounter;
    private final ConcurrentHashMap<String, Integer> globalFrequency; // общая память

    public WordCounter(Path filePath, AtomicInteger totalWordsCounter, ConcurrentHashMap<String, Integer> globalFrequency) {
        this.filePath = filePath;
        this.totalWordsCounter = totalWordsCounter;
        this.globalFrequency = globalFrequency;
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
            for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
                globalFrequency.merge(entry.getKey(), entry.getValue(), Integer::sum);
            }

            totalWordsCounter.addAndGet(wordCount); // добавляем в общий счетчик
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + filePath);
        }
    }
}
