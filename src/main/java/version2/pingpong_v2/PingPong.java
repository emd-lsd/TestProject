package main.java.version2.pingpong_v2;

import java.util.concurrent.CountDownLatch;

/**
 * Переключатели из счетчиков CountDownLatch
 */
public class PingPong {
    private static final int MAX_ITERATIONS = 15;
    private static CountDownLatch pingLatch = new CountDownLatch(1);
    private static CountDownLatch pongLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        Thread pingThread = new Thread(new Ping());
        Thread pongThread = new Thread(new Pong());

        pingThread.start();
        pongThread.start();

        // Запускаем игру, разрешая Ping начать первым
        pingLatch.countDown();
    }

    static class Ping implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < MAX_ITERATIONS; i++) {
                try {
                    pingLatch.await(); // Ждем сигнала от Pong
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Ping");
                pingLatch = new CountDownLatch(1); // Сбрасываем latch для следующей итерации
                pongLatch.countDown(); // Сигнализируем Pong
            }
        }
    }

    static class Pong implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < MAX_ITERATIONS; i++) {
                try {
                    pongLatch.await(); // Ждем сигнала от Ping
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Pong");
                pongLatch = new CountDownLatch(1); // Сбрасываем latch для следующей итерации
                pingLatch.countDown(); // Сигнализируем Ping
            }
        }
    }
}