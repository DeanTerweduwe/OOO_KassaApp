package model.KassaBonDecorator;

import model.db.WinkelKarDB;

public abstract class KassaBonDecorator implements KasssaBonInterface {
    protected KassaBon decoKassaBon;
    protected WinkelKarDB winkelKar;

    public KassaBonDecorator(KassaBon decoKassaBon, WinkelKarDB winkelKar) {
        super();
        this.decoKassaBon = decoKassaBon;
        this.winkelKar = winkelKar;
    }
}
