package test.java.ru.raiffeisen.cc.testproject.wordcountwiththreads;

import main.java.ru.raiffeisen.cc.testproject.wordcountwiththreads.globalstats.Main;
import main.java.ru.raiffeisen.cc.testproject.wordcountwiththreads.globalstats.WordCounter;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for thread-safe word counter implementation using synchronized blocks.
 * Verifies:
 * <ul>
 *   <li>Basic counting functionality</li>
 *   <li>Thread safety under concurrent access</li>
 *   <li>Proper synchronization of shared resources</li>
 *   <li>Edge cases (empty files, special characters)</li>
 * </ul>
 */
public class ThreadSyncWordCounterTest {

    @Before
    public void resetStaticState() throws Exception {
        // Reset static counters between tests
        Field globalFrequency = Main.class.getDeclaredField("globalFrequency");
        globalFrequency.setAccessible(true);
        ((Map<?, ?>) globalFrequency.get(null)).clear();

        Field totalCounter = Main.class.getDeclaredField("totalCounter");
        totalCounter.setAccessible(true);
        totalCounter.setInt(null, 0);
    }

    // Basic functionality tests
    @Test
    public void emptyFile() throws Exception {
        Path file = Paths.get("src/test/resources/threads/empty.txt");
        new WordCounter(file).run();
        assertEquals(0, Main.getTotalCounter());
        assertTrue(Main.getGlobalFrequency().isEmpty());
    }

    @Test
    public void singleWordFile() throws Exception {
        Path file = Paths.get("src/test/resources/threads/single_word.txt");
        new WordCounter(file).run();
        assertEquals(1, Main.getTotalCounter());
        assertEquals(1, (int) Main.getGlobalFrequency().get("apple"));
    }

    // Concurrency tests
    @Test
    public void concurrentUpdates() throws Exception {
        Path file = Paths.get("src/test/resources/threads/repeated.txt");
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(new WordCounter(file));
        }

        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.SECONDS);

        assertEquals(30, Main.getTotalCounter()); // 10 threads × 3 words
        assertEquals(30, (int) Main.getGlobalFrequency().get("hello"));
    }

    @Test
    public void differentFilesConcurrently() throws Exception {
        Path file1 = Paths.get("src/test/resources/threads/concurrent_test1.txt");
        Path file2 = Paths.get("src/test/resources/threads/concurrent_test2.txt");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new WordCounter(file1));
        executor.submit(new WordCounter(file2));
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        Map<String, Integer> freq = Main.getGlobalFrequency();
        assertEquals(6, Main.getTotalCounter());
        assertEquals(1, (int) freq.get("word1"));
        assertEquals(2, (int) freq.get("word2"));
        assertEquals(2, (int) freq.get("word3"));
        assertEquals(1, (int) freq.get("word4"));
    }

    // Stress test
    @Test
    public void highConcurrencyStressTest() throws Exception {
        Path file = Paths.get("src/test/resources/threads/large_file.txt");
        int threadCount = 20;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(new WordCounter(file));
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // Verify no words were lost during concurrent updates
        int expectedTotal = threadCount * countWordsInFile(file);
        assertEquals(expectedTotal, Main.getTotalCounter());
    }

    private int countWordsInFile(Path file) throws Exception {
        String content = Files.readString(file);
        return content.split("\\s+").length;
    }
}