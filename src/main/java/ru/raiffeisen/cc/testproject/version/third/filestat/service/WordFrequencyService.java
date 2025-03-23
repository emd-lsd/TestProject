package main.java.ru.raiffeisen.cc.testproject.version.third.filestat.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Сервис подсчета статистики файла
 */
public class WordFrequencyService {
    private WordFrequencyService() {
    }

    public static Map<String, Integer> calculateWordFrequency(Path filePath) throws IOException {
        String content = Files.readString(filePath);
        String[] words = content.toLowerCase().replaceAll("[^a-zа-я0-9\\s]", "").trim().split("\\s+");

        Map<String, Integer> localFrequency = new HashMap<>();
        for (String word : words) {
            localFrequency.put(word, localFrequency.getOrDefault(word, 0) + 1);
        }
        return localFrequency;
    }
}
