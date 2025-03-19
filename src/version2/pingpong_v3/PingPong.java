package version2.pingpong_v3;

import java.util.concurrent.Semaphore;

public class PingPong {
    private static final int MAX_ITERATIONS = 15;
    private static final Semaphore pingSemaphore = new Semaphore(1); // Начинаем с Ping
    private static final Semaphore pongSemaphore = new Semaphore(0); // Pong ждет

    public static void main(String[] args) {
        Thread pingThread = new Thread(new Ping());
        Thread pongThread = new Thread(new Pong());

        pingThread.start();
        pongThread.start();
    }

    static class Ping implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < MAX_ITERATIONS; i++) {
                try {
                    pingSemaphore.acquire(); // Захватываем семафор Ping
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Ping");
                pongSemaphore.release(); // Освобождаем семафор Pong
            }
        }
    }

    static class Pong implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < MAX_ITERATIONS; i++) {
                try {
                    pongSemaphore.acquire(); // Захватываем семафор Pong
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Pong");
                pingSemaphore.release(); // Освобождаем семафор Ping
            }
        }
    }
}