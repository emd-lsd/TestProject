package main.java.part3_ThreadSafe_Atomicity;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CommonStorage {
    private final ConcurrentHashMap<String, AtomicInteger> globalCounter = new ConcurrentHashMap<>();

    public void addWord(String word, int count) {
        globalCounter.computeIfAbsent(word, k -> new AtomicInteger(0)).addAndGet(count);
    }

    public void printStorage() {
        System.out.println("\nОбщая частота всех слов");
        globalCounter.forEach((word, count) ->
                System.out.printf(" %s: %d%n", word, count.get())
                );
    }
}
