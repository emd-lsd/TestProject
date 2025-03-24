package main.java.ru.raiffeisen.cc.testproject.version.wordcountnonblockingmultithread.locks.livelock;

/**
 * Класс Команды с предложением
 * В методе run реализована логика пересмотра предложения между двумя командами,
 * livelock возникает из-за неправильной логики изменения предложений,
 * закомментирована правильная логика изменения предложений, когда стороны договорятся.
 *
 * Так же есть реализован выход из livelock через подсчет попыток сравнения предложений
 * крайнее значение попыток для выхода в константе @MAX_ATTEMPTS
 */
public class Crew implements Runnable {
    private static final int MAX_ATTEMPTS = 100;
    private final String name;
    private final Negotiation negotiation;
    private Proposal selfProposal;
    private Proposal otherProposal;

    public Crew(String name, Negotiation negotiation, Proposal selfProposal) {
        this.name = name;
        this.negotiation = negotiation;
        this.selfProposal = selfProposal;
    }

    public void setOtherProposal(Proposal otherProposal) {
        this.otherProposal = otherProposal;
    }

    @Override
    public void run() {
        int attempts = 0; // Счетчик попыток
        while (!negotiation.isAgreementReached()) {
            this.toString();

            if (selfProposal.getValue() == otherProposal.getValue()) {
                negotiation.setAgreementReached(true);
                System.out.println(name + " и другая сторона пришли к соглашению!");
            } else {
                if (attempts >= MAX_ATTEMPTS) { // Выход из livelock за счет счетчика попыток
                    selfProposal.setValue(otherProposal.getValue());
                    System.out.println(name + " уступает и принимает предложение другой стороны: " + selfProposal);
                } else {
                    // Смена предложения в противоположные стороны
                    if (name.equals("Side A")) {
                        selfProposal.setValue(selfProposal.getValue() + 1);
                    } else {
                        selfProposal.setValue(selfProposal.getValue() - 1);
                    }

                    System.out.println(name + " изменяет предложение на: " + selfProposal);
                    attempts++;
                }

                // Пример правильной работы через правильную логику
//                int newValue = selfProposal.getValue() + (selfProposal.getValue() < otherProposal.getValue() ? 1 : -1);
//                selfProposal.setValue(newValue);
//                System.out.println(name + " новое значение: " + selfProposal);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public String toString() {
        return name + " предлагает: " + selfProposal;
    }
}
