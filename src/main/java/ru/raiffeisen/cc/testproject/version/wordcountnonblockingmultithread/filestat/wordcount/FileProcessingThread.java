package main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.filestat.wordcount;

import java.nio.file.Path;

public class FileProcessingThread implements Runnable {

    private final Path inputDir;

    public FileProcessingThread(Path inputDir) {
        this.inputDir = inputDir;
    }

    @Override
    public void run() {
        try {
            WordCountFileProcessor wordCountFileProcessor = new WordCountFileProcessor(inputDir);
            wordCountFileProcessor.processFiles();
        } catch (Exception e) {
            System.err.println("Ошибка при обработке файлов: " + e.getMessage());
        }
    }
}
