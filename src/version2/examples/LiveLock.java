package version2.examples;

public class LiveLock {
    private static boolean isThread1Working = true;
    private static boolean isThread2Working = true;

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            while (isThread1Working) {
                System.out.println("Thread 1: Trying to do work...");
                if (isThread2Working) {
                    System.out.println("Thread 1: Thread 2 is working, I'll wait...");
                    try {
                        Thread.sleep(100); // Имитация ожидания
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue; // Продолжаем ждать
                }
                System.out.println("Thread 1: Doing work!");
                isThread1Working = false; // Завершаем работу
            }
        });

        Thread thread2 = new Thread(() -> {
            while (isThread2Working) {
                System.out.println("Thread 2: Trying to do work...");
                if (isThread1Working) {
                    System.out.println("Thread 2: Thread 1 is working, I'll wait...");
                    try {
                        Thread.sleep(100); // Имитация ожидания
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue; // Продолжаем ждать
                }
                System.out.println("Thread 2: Doing work!");
                isThread2Working = false; // Завершаем работу
            }
        });

        thread1.start();
        thread2.start();
    }
}