package main.java.locks.livelock;

/**
 * Класс преговоров,
 * создает потоки с экземплярами класса Команды
 * при запуске потоков - переговоры начинаются.
 *
 * Имеет методы установления соглашения между командами (agreementReached).
 */
public class Negotiation {
    private boolean agreementReached = false;

    public void startNegotiation() {
        Proposal proposalA = new Proposal(20);
        Proposal proposalB = new Proposal(10);

        Crew crewA = new Crew("Side A", this, proposalA);
        Crew crewB = new Crew("Side B", this, proposalB);

        crewA.setOtherProposal(proposalB);
        crewB.setOtherProposal(proposalA);

        Thread threadA = new Thread(crewA);
        Thread threadB = new Thread(crewB);

        threadA.start();
        threadB.start();
    }

    public boolean isAgreementReached() {
        return agreementReached;
    }

    public void setAgreementReached(boolean agreementReached) {
        this.agreementReached = agreementReached;
    }
}
