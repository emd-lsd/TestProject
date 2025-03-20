package main.java.version.third.filestat.partthird;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Третья версия задачи на многопоточный подсчет слов в файлах
 * Для сохранения результатов слов от каждого файла, каждому таску передается ConcurrentLinkedQueue,
 * которая является реализацией потокобезопасной неблокирующей очереди.
 * Для сохранения статистики каждого файла используется та же очередь из мап статистик каждого файла.
 * Для удобного хранения оберток информации каждого файла присутствует еще тоже неблокирующая очередь
 * состоящая из оберток каждого файла, содержащая количество слов и локальную статистику.
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
