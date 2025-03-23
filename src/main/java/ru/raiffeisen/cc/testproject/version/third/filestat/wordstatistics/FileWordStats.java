package main.java.ru.raiffeisen.cc.testproject.version.third.filestat.wordstatistics;

import java.util.Map;

/**
 * Container for file statistics
 */
public class FileWordStats {
    private final String fileName;
    private final int wordCount;
    private final Map<String, Integer> wordFrequency;

    public FileWordStats(String fileName, int wordCount, Map<String, Integer> wordFrequency) {
        this.fileName = fileName;
        this.wordCount = wordCount;
        this.wordFrequency = wordFrequency;
    }

    public String getFileName() {
        return fileName;
    }

    public Map<String, Integer> getWordFrequency() {
        return wordFrequency;
    }

    public void printWordFrequency() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("Файл: %s - %d слов%n", fileName, wordCount));
        wordFrequency.forEach((word, count) -> result.append(String.format(" %s: %d%n", word, count)));
        System.out.println(result);
    }

    public void printWordCount() {
        System.out.printf("Файл: %s - %d слов%n", fileName, wordCount);
    }
}
