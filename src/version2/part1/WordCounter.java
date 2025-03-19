package version2.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WordCounter implements Runnable {
    private final Path filePath;

    public WordCounter(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            String content = Files.readString(filePath);
            String[] words = content.toLowerCase()
                    .replaceAll("[^a-zа-я0-9\\s]", "")
                    .trim()
                    .split("\\s+");
            int wordCount = words.length;

            // Добавление в общий счетчик
            Main.addToTotalCounter(wordCount);

            System.out.printf("Файл: %s - %d слов%n", filePath.getFileName(), wordCount);
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + filePath);
        }
    }
}
