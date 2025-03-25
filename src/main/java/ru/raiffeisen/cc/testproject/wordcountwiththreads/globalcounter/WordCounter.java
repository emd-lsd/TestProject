package main.java.ru.raiffeisen.cc.testproject.wordcountwiththreads.globalcounter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

            // Add to common counter
            Main.addToTotalCounter(wordCount);

            System.out.printf("Файл: %s - %d слов%n", filePath.getFileName(), wordCount);
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + filePath);
        }
    }
}
