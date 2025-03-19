package part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    //static volatile Integer testCounter = 0;

    public static void main(String[] args) throws IOException {
        Path inputDir = Paths.get("input");
        AtomicInteger totalWordsCounter = new AtomicInteger(0);
        Integer testCounter = 0;


        try {
            List<Path> files = Files.list(inputDir)
                    .filter(Files::isRegularFile)
                    .toList();
            ExecutorService executorService = Executors.newFixedThreadPool(files.size());

            for (Path file : files) {
                Future<Integer> future = executorService.submit(new WordCounter(file, testCounter));
                testCounter += future.get();
            }

            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);

            System.out.printf("Общее количество слов со всех файлов: %d%n", totalWordsCounter.get());
            System.out.println("Общее количество testCounter " + testCounter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}


// TODO: Переписать решение с Threads с реализацией логики через HB
// TODO: Решить задачку с Ping Pong, чтобы каждый поток из 2ух писал по очереди ping pong (минимум 2 способа)
// TODO: Переделать третью задачу через synchronized и notify