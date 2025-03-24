package main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.locks.livelock;

/**
 * Класс предложения стороны
 */
public class Proposal {
    private int value;

    public Proposal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Proposal { value = " + value + " }";
    }
}
