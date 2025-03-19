package main.java.part3_ThreadSafe_Atomicity;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        Path inputDir = Paths.get("src/main/resources/input");
        AtomicInteger totalWordsCounter = new AtomicInteger(0);
        CommonStorage commonStorage = new CommonStorage();

        try {
            List<Path> files = Files.list(inputDir)
                    .filter(Files::isRegularFile)
                    .toList();
            ExecutorService executorService = Executors.newFixedThreadPool(files.size());

            for (Path file : files) {
                executorService.submit(new WordCounter(file, totalWordsCounter, commonStorage));
            }

            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);

            System.out.printf("Общее количество слов со всех файлов: %d%n", totalWordsCounter.get());

            commonStorage.printStorage();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}