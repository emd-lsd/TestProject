package main.java.ru.raiffeisen.cc.testproject.locks.deadlock;

/**
 * Класс управления банковскими операциями
 * Создает задачи на перевод и запускает их в разных потоках
 */
public class Bank {
    private final Account accountA;
    private final Account accountB;

    public Bank(Account accountA, Account accountB) {
        this.accountA = accountA;
        this.accountB = accountB;
    }

    public void startTransfers() {
        // Создаем задачи для перевода денег в обоих направлениях
        TransferTask task1 = new TransferTask(accountA, accountB, 100);
        TransferTask task2 = new TransferTask(accountB, accountA, 100);

        // Создаем потоки для выполнения задач
        Thread thread1 = new Thread(task1, "Thread-1");
        Thread thread2 = new Thread(task2, "Thread-2");

        // Запускаем потоки
        thread1.start();
        thread2.start();
    }
}