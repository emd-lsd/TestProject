package main.java.ru.raiffeisen.cc.testproject.version.third.locks.deadlock;

/**
 * Класс задачи на перевод средств между счетами
 * Deadlock из-за взаимного ожидания от счета получателя to
 * Устранение deadlock через добавление таймаута
 */
public class TransferTask implements Runnable {
    private final Account from;
    private final Account to;
    private final double amount;

    public TransferTask(Account from, Account to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            // Захватываем блокировку первого счета
            synchronized (from) {
                System.out.println(Thread.currentThread().getName() + " захватил " + from.getName());

                // Ждем уведомления от второго счета
                System.out.println(Thread.currentThread().getName() + " ждет уведомления от " + to.getName());
                from.waitForTransfer();

                // Рабочий вариант с таймаутом по ожиданию
//                from.waitForTransfer(100);

//                // Пытаемся уведомить второй счет
//                synchronized (to) {
//                    System.out.println(Thread.currentThread().getName() + " уведомляет " + to.getName());
//                    to.notifyTransfer();
//                }

                // Выполняем перевод
                from.withdraw(amount);
                to.deposit(amount);

                System.out.println("Перевод выполнен: " + amount + " с " + from.getName() + " на " + to.getName());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
