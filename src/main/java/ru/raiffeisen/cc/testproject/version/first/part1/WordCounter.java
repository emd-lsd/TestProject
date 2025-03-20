package main.java.ru.raiffeisen.cc.testproject.version.first.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

public class WordCounter implements Callable {
    private final Path filePath;
    //private final AtomicInteger totalWordsCounter;
    private Integer testCounter;
    private int counter = 0;
    private int wordCount = 0;

    public WordCounter(Path filePath, Integer testCounter) {
        this.filePath = filePath;
        //this.totalWordsCounter = totalWordsCounter;
        this.testCounter = testCounter;
    }

    //@Override
    public void run() {
        try {
            String content = Files.readString(filePath);
            String[] words = content.toLowerCase()
                    .replaceAll("[^a-zа-я0-9\\s]", "")
                    .trim()
                    .split("\\s+");
            int wordCount = words.length;

            System.out.printf("Файл: %s - %d слов%n", filePath.getFileName(), wordCount);

            //totalWordsCounter.addAndGet(wordCount); // добавляем в общий счетчик


            testCounter = testCounter + wordCount;
            counter += wordCount;
            System.out.println(testCounter);
            System.out.println("counter: " + counter);



        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + filePath);
        }
    }


    @Override
    public Object call() throws Exception {
        try {
            String content = Files.readString(filePath);
            String[] words = content.toLowerCase()
                    .replaceAll("[^a-zа-я0-9\\s]", "")
                    .trim()
                    .split("\\s+");
            wordCount = words.length;

            System.out.printf("Файл: %s - %d слов%n", filePath.getFileName(), wordCount);

            //totalWordsCounter.addAndGet(wordCount); // добавляем в общий счетчик


            testCounter = testCounter + wordCount;
            counter += wordCount;
            System.out.println(testCounter);
            System.out.println("counter: " + counter);



        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + filePath);
        }
        return wordCount;
    }
}
