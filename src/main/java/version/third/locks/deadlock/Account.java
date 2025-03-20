package main.java.version.third.locks.deadlock;

/**
 * Класс банковского счёта
 */
public class Account {
    private final String name;
    private double balance;

    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
    }

    public synchronized void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Недостаточно средств на счете " + name);
        }
    }

    public synchronized void waitForTransfer() throws InterruptedException {
        wait(); // Ждем уведомления от другого потока
    }

    public synchronized void waitForTransfer(long time) throws InterruptedException {
        wait(time);
    }

    public synchronized void notifyTransfer() {
        notify(); // Уведомляем ожидающий поток
    }
}
