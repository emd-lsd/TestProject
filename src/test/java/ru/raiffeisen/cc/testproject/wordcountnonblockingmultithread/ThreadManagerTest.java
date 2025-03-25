package test.java.ru.raiffeisen.cc.testproject.wordcountnonblockingmultithread;

import main.java.ru.raiffeisen.cc.testproject.wordcountnonblockingmultithread.service.ThreadManager;
import org.junit.Test;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.Assert.*;

/**
 * Unit tests for {@link ThreadManager} class functionality.
 *
 * <p>Verifies the thread management capabilities including:</p>
 * <ul>
 *   <li>Proper thread creation and execution</li>
 *   <li>Correct handling of file processing tasks</li>
 *   <li>Synchronization of multiple threads</li>
 * </ul>
 *
 * <p>Test cases focus on validating the core threading behavior
 * rather than actual file processing logic.</p>
 */
public class ThreadManagerTest {

    @Test
    public void testProcessFiles() throws Exception {
        List<Path> files = List.of(
                Paths.get("src/test/resources/nonblockingmultithread/single_word.txt"),
                Paths.get("src/test/resources/nonblockingmultithread/repeated.txt")
        );

        AtomicInteger processedFiles = new AtomicInteger(0);

        ThreadManager.processFiles(files, file -> () -> {
            processedFiles.incrementAndGet();
            System.out.println("Processing: " + file);
        });

        assertEquals(2, processedFiles.get());
    }
}
