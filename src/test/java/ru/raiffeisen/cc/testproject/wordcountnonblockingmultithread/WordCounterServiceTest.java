package test.java.ru.raiffeisen.cc.testproject.wordcountnonblockingmultithread;

import main.java.ru.raiffeisen.cc.testproject.wordcountnonblockingmultithread.service.WordCounterService;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link WordCounterService} functionality.
 *
 * <p>Verifies core word counting logic including:</p>
 * <ul>
 *   <li>Basic word counting in files</li>
 *   <li>Handling of repeated words</li>
 *   <li>Proper file reading operations</li>
 * </ul>
 */
public class WordCounterServiceTest {

    @Test
    public void whenHasFile_checkCounterService() throws Exception {
        Path file = Paths.get("src/test/resources/nonblockingmultithread/repeated.txt");
        assertEquals(3, WordCounterService.countWords(file));
    }
}
