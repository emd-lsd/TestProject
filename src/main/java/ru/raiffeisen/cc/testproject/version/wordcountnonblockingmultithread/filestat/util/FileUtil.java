package main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.filestat.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileUtil {
    /**
     * Returns a list of all regular files in the specified directory.
     *
     * @param directoryPath Path to the directory.
     * @return List of regular file paths.
     * @throws IOException If an error occurs while reading the directory.
     */
    public static List<Path> getFilesFromDirectory(Path directoryPath) throws IOException {
        return Files.list(directoryPath)
                .filter(Files::isRegularFile)
                .toList();
    }
}