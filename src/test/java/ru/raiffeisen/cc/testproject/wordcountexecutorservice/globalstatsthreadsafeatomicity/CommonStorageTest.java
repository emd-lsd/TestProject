package test.java.ru.raiffeisen.cc.testproject.wordcountexecutorservice.globalstatsthreadsafeatomicity;

import main.java.ru.raiffeisen.cc.testproject.wordcountexecutorservice.globalstatsthreadsafeatomicity.CommonStorage;
import main.java.ru.raiffeisen.cc.testproject.wordcountexecutorservice.globalstatsthreadsafeatomicity.WordCounter;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link CommonStorage} class functionality, verifying:
 * <ul>
 *   <li>Basic word counting operations</li>
 *   <li>Thread-safe behavior under concurrent access</li>
 *   <li>Integration with {@link WordCounter} class</li>
 * </ul>
 */
public class CommonStorageTest {

    @Test
    public void addWordShouldIncrementCounter() {
        CommonStorage storage = new CommonStorage();
        storage.addWord("test", 2);
        storage.addWord("test", 3);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        storage.printStorage();
        System.setOut(System.out);

        assertTrue(out.toString().contains("test: 5"));
    }

    @Test
    public void concurrentAddWord() throws Exception {
        CommonStorage storage = new CommonStorage();
        int threadsCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);

        for (int i = 0; i < threadsCount; i++) {
            executor.submit(() -> storage.addWord("java", 1));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        // Check by output
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        try {
            storage.printStorage();
            String output = out.toString();
            assertTrue(output.contains("java: " + threadsCount));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void concurrentWordCounters() throws Exception {
        // Create temporary file
        Path testFile = Files.createTempFile("test", ".txt");
        Files.write(testFile, Collections.singletonList("java"));

        CommonStorage storage = new CommonStorage();
        AtomicInteger total = new AtomicInteger(0);
        int threadsCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);

        // 10 threads process 1 file
        for (int i = 0; i < threadsCount; i++) {
            executor.submit(new WordCounter(testFile, total, storage));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        // Check by output
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        try {
            storage.printStorage();
            String output = out.toString();
            assertTrue(output.contains("java: " + threadsCount));
        } finally {
            System.setOut(originalOut);
            Files.delete(testFile); // delete temporary file
        }
    }

    @Test
    public void multipleThreadsShouldCorrectlyUpdateCounters() throws Exception {
        Path file1 = Paths.get("src/test/resources/executorservice/concurrenttest/single_word.txt");
        Path file2 = Paths.get("src/test/resources/executorservice/concurrenttest/repeated.txt");
        AtomicInteger total = new AtomicInteger(0);
        CommonStorage storage = new CommonStorage();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new WordCounter(file1, total, storage));
        executor.submit(new WordCounter(file2, total, storage));
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        assertEquals(4, total.get()); // 1 (apple) + 3 (hello)

        // Checking frequency by output (without breaking encapsulation)
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        try {
            storage.printStorage();
            String output = out.toString(StandardCharsets.UTF_8);

            assertTrue(output.contains("apple: 1"));
            assertTrue(output.contains("hello: 3"));
        } finally {
            System.setOut(originalOut);
        }
    }
}
