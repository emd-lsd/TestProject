package main.java.version.third.filestat.partfirst;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Первая версия задачи на многопоточный подсчет слов в файлах
 * Для сохранения результатов слов от каждого файла, каждому таску передается ConcurrentLinkedQueue,
 * которая является реализацией потокобезопасной неблокирующей очереди.
 *
 * Точка входа в программу
 * Задается директория с файлами и создается процессор управления обработкой файлов
 */
public class Main {
    public static void main(String[] args) {
        Path inputDir = Paths.get("src/main/resources/input");

        FileProcessor fileProcessor = new FileProcessor(inputDir);
        fileProcessor.processFiles();
    }
}
