package test.java.ru.raiffeisen.cc.testproject.wordcountnonblockingmultithread;

import main.java.ru.raiffeisen.cc.testproject.wordcountnonblockingmultithread.wordstatistics.WordCountFileProcessor;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Integration tests for the complete word counting pipeline using non-blocking queues.
 *
 * <p>This test class verifies the end-to-end functionality of the word counting system,
 * including file processing, word counting, statistics aggregation, and thread-safe
 * queue operations.</p>
 *
 * <p>Key verification points:</p>
 * <ul>
 *   <li>Correct total word count across all processed files</li>
 *   <li>Proper aggregation of word frequency statistics</li>
 *   <li>Null-safety in queue operations</li>
 *   <li>Non-empty results validation</li>
 * </ul>
 */
public class WordCountIntegrationTest {

    private Path testInputDir;
    private WordCountFileProcessor processor;

    @Before
    public void setUp() throws Exception {
        testInputDir = Paths.get("src/test/resources/nonblockingmultithread");
        processor = new WordCountFileProcessor(testInputDir);
    }

    @Test
    public void whenHasWordCountFileProcessor_thenCheckGlobalStatsForEveryWord() throws Exception {
        processor.processFiles();

        int totalCount = processor.getCountsQueue().stream().filter(Objects::nonNull).mapToInt(Integer::intValue).sum();
        assertEquals(11, totalCount); // 4 + 3 + 4 + 1

        Map<String, Integer> globalStats = new HashMap<>();
        processor.getGlobalWordStats().forEach(stat -> {
            if (stat != null) {
                stat.forEach((word, count) -> globalStats.merge(word, count, Integer::sum));
            }
        });

        assertEquals(2, (int) globalStats.getOrDefault("java", 0));
        assertEquals(3, (int) globalStats.getOrDefault("hello", 0));
        assertEquals(4, (int) globalStats.getOrDefault("test", 0));
        assertEquals(0, (int) globalStats.getOrDefault("word", 0));

        assertNotNull("Global stats should not be null", globalStats);
        assertFalse("Global stats should not be empty", globalStats.isEmpty());
    }
}