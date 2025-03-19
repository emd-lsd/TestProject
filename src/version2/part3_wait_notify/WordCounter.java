package version2.part3_wait_notify;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class WordCounter implements Runnable {
    private final Path filePath;

    public WordCounter(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            String content = Files.readString(filePath);
            String[] words = content.toLowerCase().replaceAll("[^a-zа-я0-9\\s]", "").trim().split("\\s+");
            int wordCount = words.length;

            Map<String, Integer> localFrequency = new HashMap<>();
            for (String word : words) {
                localFrequency.put(word, localFrequency.getOrDefault(word, 0) + 1);
            }

            // Обновление глобальной статистики
            Main.updateGlobalWordFrequency(localFrequency);

            // Добавление в общий счетчик
            Main.addToTotalCounter(wordCount);

            // Вывод локальной статистики
            StringBuilder result = new StringBuilder();
            result.append(String.format("Файл: %s - %d слов%n", filePath.getFileName(), wordCount));
            localFrequency.forEach((word, count) -> result.append(String.format(" %s: %d%n", word, count)));
            System.out.println(result);

            Main.threadCompleted(); // уведомление, что поток завершил работу

//            Main.updateGlobalThread(localFrequency, wordCount);

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + filePath);
        }
    }
}
