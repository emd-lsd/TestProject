package main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.locks.deadlock;

/**
 * Точка входа в программу
 * Создаются экземпляры двух счетов и создаем операцию на перевод
 */
public class Main {
    public static void main(String[] args) {
        // Создаем два счета
        Account accountA = new Account("AccountA", 1000);
        Account accountB = new Account("AccountB", 1000);

        // Создаем банк и запускаем переводы
        Bank bank = new Bank(accountA, accountB);
        bank.startTransfers();
    }
}