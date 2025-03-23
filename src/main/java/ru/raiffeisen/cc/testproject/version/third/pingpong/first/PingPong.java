package main.java.ru.raiffeisen.cc.testproject.version.third.pingpong.first;

/**
 * Использование общего объекта для лока
 * флаг pingTurn для определения порядка
 */
public class PingPong {

    private static final Object lock = new Object();
    private static boolean pingTurn = true;

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
                synchronized (lock) {
                    while (!pingTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println("Ping");
                    pingTurn = false; // Передает очередь pong
                    lock.notifyAll(); // Разблокирует поток Pong
                }
            }
        }
    }

    static class Pong implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 15; i++) {
                synchronized (lock) {
                    while (pingTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println("Pong");
                    pingTurn = true; // Передает очередь ping
                    lock.notifyAll(); // Разблокирует поток Ping
                }
            }
        }
    }
}


// TODO: вынести все в разные класссы и добавить абстракицю через интерфейсы
// TODO: семафор и countdownlatch в двойную абстракцию, а блокировку synchronized & reentrantlock в другую обычную абстракцию
// TODO: ну и нейминг пакетов по логике задач а не по нумерации