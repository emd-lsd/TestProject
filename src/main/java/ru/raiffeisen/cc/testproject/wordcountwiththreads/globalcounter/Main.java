package main.java.ru.raiffeisen.cc.testproject.wordcountwiththreads.globalcounter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Start point for multithreaded counting words by Threads with blocking using synchronizations
 */
public class Main {
    private static int totalCounter = 0;

    public static void main(String[] args) throws IOException {
        Path inputDir = Paths.get("src/main/resources/input");

        try {
            List<Path> files = Files.list(inputDir).filter(Files::isRegularFile).toList();

            List<Thread> threads = new ArrayList<>();
            for (Path file : files) {
                Thread thread = new Thread(new WordCounter(file));
                threads.add(thread);
                thread.start();
            }
            for (Thread thread : threads) {
                thread.join();
            }

            System.out.printf("Общее количество слов во всех файла: %d%n", totalCounter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static synchronized void addToTotalCounter(int count) {
        totalCounter += count;
    }

}


// TODO: Переписать решение с Threads с реализацией логики через HB
// TODO: Решить задачку с Ping Pong, чтобы каждый поток из 2ух писал по очереди ping pong (минимум 2 способа)
// TODO: Переделать третью задачу через synchronized и notify