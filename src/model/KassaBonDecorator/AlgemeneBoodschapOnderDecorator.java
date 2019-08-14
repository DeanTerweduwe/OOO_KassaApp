package model.KassaBonDecorator;

import model.db.WinkelKarDB;

public class AlgemeneBoodschapOnderDecorator extends KassaBonDecorator{

    private String boodschap;

    public AlgemeneBoodschapOnderDecorator(KassaBon decoKassaBon, String boodschap) {
        super(decoKassaBon, null);
        this.boodschap = boodschap;
    }

    @Override
    public void drukAf() {
        decoKassaBon.drukAf();
        System.out.println("********************************* \n"+ boodschap+"\n********************************* ");
    }
}
