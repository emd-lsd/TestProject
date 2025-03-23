package main.java.ru.raiffeisen.cc.testproject.version.third.filestat.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Сервис для подсчета слов в файле
 *
 */
public class WordCounterService {

    // приватный конструктор для запрета создания экземпляров
    private WordCounterService() {
    }

    public static int countWords (Path filePath) throws IOException {
        String content = Files.readString(filePath);
        String[] words = content.toLowerCase()
                .replaceAll("[^a-zа-я0-9\\s]", "")
                .trim()
                .split("\\s+");
        return words.length;
    }
}
