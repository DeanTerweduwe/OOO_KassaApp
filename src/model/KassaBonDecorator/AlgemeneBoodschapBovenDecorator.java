package model.KassaBonDecorator;

public class AlgemeneBoodschapBovenDecorator extends KassaBonDecorator {

    private String boodschap;

    public AlgemeneBoodschapBovenDecorator(KassaBon decoKassaBon, String boodschap) {
        super(decoKassaBon, null);
        this.boodschap = boodschap;
    }

    @Override
    public void drukAf() {
        System.out.println("********************************* \n"+ boodschap+"\n********************************* ");

        decoKassaBon.drukAf();
    }
}
