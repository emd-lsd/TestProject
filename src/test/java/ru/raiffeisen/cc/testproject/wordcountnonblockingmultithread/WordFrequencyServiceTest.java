package test.java.ru.raiffeisen.cc.testproject.wordcountnonblockingmultithread;

import main.java.ru.raiffeisen.cc.testproject.wordcountnonblockingmultithread.service.WordFrequencyService;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link WordFrequencyService} functionality.
 *
 * <p>Verifies word frequency calculation logic including:</p>
 * <ul>
 *   <li>Accurate word frequency counting</li>
 *   <li>Case-insensitive word grouping</li>
 *   <li>Proper handling of mixed-case input</li>
 *   <li>Correct summation of total word occurrences</li>
 * </ul>
 */
public class WordFrequencyServiceTest {
    @Test
    public void whenHasFileWithMixOfWords_thenCheckFrequency() throws Exception {
        Path file = Paths.get("src/test/resources/nonblockingmultithread/mixed.txt");
        Map<String, Integer> freq = WordFrequencyService.calculateWordFrequency(file);

        assertEquals(4, freq.values().stream().mapToInt(Integer::intValue).sum());
        assertEquals(2, (int) freq.get("java"));
        assertEquals(2, (int) freq.get("test"));
    }
}
