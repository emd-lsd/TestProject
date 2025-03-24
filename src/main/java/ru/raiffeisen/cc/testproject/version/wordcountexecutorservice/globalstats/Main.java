package main.java.ru.raiffeisen.cc.testproject.version.wordcountexecutorservice.globalstats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws IOException {
        Path inputDir = Paths.get("src/main/resources/input");
        AtomicInteger totalWordsCounter = new AtomicInteger(0);
        ConcurrentHashMap<String, Integer> globalFrequency = new ConcurrentHashMap<>();

        try {
            List<Path> files = Files.list(inputDir)
                    .filter(Files::isRegularFile)
                    .toList();
            ExecutorService executorService = Executors.newFixedThreadPool(files.size());

            for (Path file : files) {
                executorService.submit(new WordCounter(file, totalWordsCounter, globalFrequency));
            }

            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);

            System.out.printf("Общее количество слов со всех файлов: %d%n", totalWordsCounter.get());
            StringBuilder result = new StringBuilder();
            result.append("Общая частота всех слов");
            globalFrequency.forEach((word, count) ->
                    result.append(String.format(" %s: %d%n", word, count))
            );
            System.out.println(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}