package version2.pingpong_v4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Переключение с помощью захвата ReentrantLock и 2 состояний и дополнительной проверкой очередности через флаг
 */
public class PingPong {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition pingCondition = lock.newCondition();
    private static final Condition pongCondition = lock.newCondition();
    private static boolean pingTurn = true; // Флаг для определения очереди

    public static void main(String[] args) {
        Thread pingThread = new Thread(new Ping());
        Thread pongThread = new Thread(new Pong());

        pingThread.start();
        pongThread.start();
    }

    static class Ping implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 15; i++) {
                lock.lock(); // Захватываем блокировку
                try {
                    while (!pingTurn) {
                        try {
                            pingCondition.await(); // Ждем, пока не наступит очередь Ping
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println("Ping");
                    pingTurn = false; // Передаем очередь Pong
                    pongCondition.signal(); // Уведомляем Pong
                } finally {
                    lock.unlock(); // Освобождаем блокировку
                }
            }
        }
    }

    static class Pong implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 15; i++) {
                lock.lock(); // Захватываем блокировку
                try {
                    while (pingTurn) {
                        try {
                            pongCondition.await(); // Ждем, пока не наступит очередь Pong
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println("Pong");
                    pingTurn = true; // Передаем очередь Ping
                    pingCondition.signal(); // Уведомляем Ping
                } finally {
                    lock.unlock(); // Освобождаем блокировку
                }
            }
        }
    }
}