package main.java.version.third.locks.livelock;

/**
 * Точка входа в программу, создает экземпляр класса переговоров.
 * Запускает переговоры.
 */
public class Main {
    public static void main(String[] args) {
        Negotiation negotiation = new Negotiation();
        negotiation.startNegotiation();
    }
}
