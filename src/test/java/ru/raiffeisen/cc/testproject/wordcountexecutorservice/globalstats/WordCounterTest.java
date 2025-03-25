package test.java.ru.raiffeisen.cc.testproject.wordcountexecutorservice.globalstats;

import main.java.ru.raiffeisen.cc.testproject.wordcountexecutorservice.globalstats.WordCounter;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link WordCounter} class functionality including:
 * <ul>
 *   <li>Basic word counting logic</li>
 *   <li>Special characters handling</li>
 *   <li>Cyrillic text processing</li>
 *   <li>Multithreaded execution scenarios</li>
 * </ul>
 */
public class WordCounterTest {

    @Test
    public void emptyFile() throws Exception {
        Path file = Paths.get("src/test/resources/executorservice/logictest/empty.txt");
        AtomicInteger total = new AtomicInteger(0);
        ConcurrentHashMap<String, Integer> freq = new ConcurrentHashMap<>();

        new WordCounter(file, total, freq).run();

        assertEquals(0, total.get());
        assertTrue(freq.isEmpty());
    }

    @Test
    public void fileWithOneWord() throws Exception {
        Path file = Paths.get("src/test/resources/executorservice/logictest/single.txt");
        AtomicInteger total = new AtomicInteger(0);
        ConcurrentHashMap<String, Integer> freq = new ConcurrentHashMap<>();

        new WordCounter(file, total, freq).run();

        assertEquals(1, total.get());
        assertEquals(1, (int) freq.get("apple"));
    }

    @Test
    public void fileWithSpecialChars() throws Exception {
        Path file = Paths.get("src/test/resources/executorservice/logictest/symbols.txt");
        AtomicInteger total = new AtomicInteger(0);
        ConcurrentHashMap<String, Integer> freq = new ConcurrentHashMap<>();

        new WordCounter(file, total, freq).run();

        assertEquals(3, total.get());
        assertEquals(2, (int) freq.get("java"));
        assertEquals(1, (int) freq.get("python"));
    }

    @Test
    public void cyrillicWords() throws Exception {
        Path file = Paths.get("src/test/resources/executorservice/concurrenttest/cyrillic.txt");
        AtomicInteger total = new AtomicInteger(0);
        ConcurrentHashMap<String, Integer> freq = new ConcurrentHashMap<>();

        new WordCounter(file, total, freq).run();

        assertEquals(3, total.get());
        assertEquals(2, (int) freq.get("привет"));
        assertEquals(1, (int) freq.get("пока"));
    }

    @Test
    public void concurrentProcessing() throws Exception {
        Path file = Paths.get("src/test/resources/executorservice/concurrenttest/repeated.txt");
        AtomicInteger total = new AtomicInteger(0);
        ConcurrentHashMap<String, Integer> freq = new ConcurrentHashMap<>();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new WordCounter(file, total, freq));
        executor.submit(new WordCounter(file, total, freq));
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        assertEquals(6, total.get());
        assertEquals(6, (int) freq.get("hello"));
    }

    @Test
    public void concurrentDifferentFiles() throws Exception {
        Path file1 = Paths.get("src/test/resources/executorservice/concurrenttest/single_word.txt");
        Path file2 = Paths.get("src/test/resources/executorservice/concurrenttest/repeated.txt");
        AtomicInteger total = new AtomicInteger(0);
        ConcurrentHashMap<String, Integer> freq = new ConcurrentHashMap<>();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new WordCounter(file1, total, freq));
        executor.submit(new WordCounter(file2, total, freq));
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        assertEquals(4, total.get()); // 1 (apple) + 3 (hello)
        assertEquals(3, (int) freq.get("hello"));
        assertEquals(1, (int) freq.get("apple"));
    }
}
