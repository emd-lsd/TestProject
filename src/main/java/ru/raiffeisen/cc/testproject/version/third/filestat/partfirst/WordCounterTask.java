package main.java.ru.raiffeisen.cc.testproject.version.third.filestat.partfirst;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

/**
 * Класс задачи потока для обработки каждого файла
 */
public class WordCounterTask implements Runnable{
    private final Path filePath;
    private final Queue<Integer> countsQueue;

    public WordCounterTask(Path filePath, Queue<Integer> countsQueue) {
        this.filePath = filePath;
        this.countsQueue = countsQueue;
    }

    @Override
    public void run() {
        try {
            int wordCount = WordCounterService.countWords(filePath);
            countsQueue.add(wordCount);
            System.out.printf("Файл: %s - %d слов%n", filePath.getFileName(), wordCount);
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + filePath);
        }
    }
}
