package main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.filestat.wordcount;

import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.filestat.controller.ThreadManager;
import main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.filestat.util.FileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Сlass for file processing with threads
 */
public class WordCountFileProcessor {
    private final Path inputDir;
    private final Queue<Integer> countsQueue = new ConcurrentLinkedQueue<>();

    public WordCountFileProcessor(Path inputDir) {
        this.inputDir = inputDir;
    }

    public void processFiles() {
        try {
            List<Path> files = FileUtil.getFilesFromDirectory(inputDir); // get list of Paths
            ThreadManager.processFiles(files, file -> new WordCounterTask(file, countsQueue)); // manage threads

            int totalCount = countsQueue.stream().mapToInt(Integer::intValue).sum();
            System.out.printf("Общее количество слов во всех файла: %d%n", totalCount);
        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка обработки файлов: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}


// TODO: переименовать этот файл в более понятный отвечающий целям класса @
// TODO: вынести создание потоков в приватныйц метод здесь и util для получения списка PATH @
// TODO: переименовать пакеты в название задачи и верхние версии пакетов в название логики версий и вынести общие части @
// TODO: добавить вызов из другого потока а не из главного потока main @

