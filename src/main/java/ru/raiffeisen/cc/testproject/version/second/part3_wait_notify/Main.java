package main.java.ru.raiffeisen.cc.testproject.version.second.part3_wait_notify;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final Map<String, Integer> globalFrequency = new HashMap<>();
    private static final Object lock = new Object(); // объект блокировки
    private static volatile int totalCounter = 0;
    private static int completedThreads = 0;
    private static int totalThreads;

    public static void main(String[] args) throws IOException {
        Path inputDir = Paths.get("src/main/resources/input");

        try {
            List<Path> files = Files.list(inputDir).filter(Files::isRegularFile).toList();

            totalThreads = files.size();

            List<Thread> threads = new ArrayList<>();
            for (Path file : files) {
                Thread thread = new Thread(new WordCounter(file));
                threads.add(thread);
                thread.start();
            }
//            for (Thread thread : threads) {
//                thread.join(); // гарантируем порядок выполнения потоков
//            }

            // Ожидание завершения всех потоков
            synchronized (lock) {
                while (completedThreads < totalThreads) {
                    lock.wait();
                }
            }

            System.out.printf("Общее количество слов во всех файла: %d%n", totalCounter);

            System.out.println("\nСтатистика слов по всем файлам:");
            globalFrequency.forEach((word, count) -> System.out.printf("  %s: %d%n", word, count));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void updateGlobalWordFrequency(Map<String, Integer> localFrequency) {
        synchronized (lock) {
            for (Map.Entry<String, Integer> entry : localFrequency.entrySet()) {
                globalFrequency.put(entry.getKey(), globalFrequency.getOrDefault(entry.getKey(), 0) + entry.getValue());
            }
        }
    }

    protected static synchronized void addToTotalCounter(int count) {
        totalCounter += count;
    }

    protected static void threadCompleted() {
        synchronized (lock) {
            completedThreads++;
            if (completedThreads >= totalThreads) {
                lock.notifyAll();
            }
        }
    }

    protected static void updateGlobalThread(Map<String, Integer> localFrequency, int count) {
        synchronized (lock) {
            for (Map.Entry<String, Integer> entry : localFrequency.entrySet()) {
                globalFrequency.put(entry.getKey(), globalFrequency.getOrDefault(entry.getKey(), 0) + entry.getValue());
            }
            totalCounter += count;
            completedThreads++;

            System.out.println("Поток завершился: " + completedThreads + "/" + totalThreads);

            if (completedThreads >= totalThreads) {
                lock.notifyAll();
            }
        }
    }

}


// TODO: Переписать решение с Threads с реализацией логики через HB
// TODO: Решить задачку с Ping Pong, чтобы каждый поток из 2ух писал по очереди ping pong (минимум 2 способа)
// TODO: Переделать третью задачу через synchronized и notify