package model.KassaBonDecorator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Artikel;
import model.db.DBService;
import model.db.WinkelKarDB;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class KassaBon implements KasssaBonInterface{
    private WinkelKarDB winkelkar ;


    public KassaBon(WinkelKarDB winkelkar) {
        this.winkelkar = winkelkar;
    }


    @Override
    public void drukAf() {

        ArrayList<Artikel> data = winkelkar.getAllArtikels();
        Set<Artikel> set = new HashSet<>(data);
        data.clear();
        data.addAll(set);

        System.out.println("Omschrijving \t\tAantal\t\tPrijs \r\n*********************************");
        for (Artikel a:data) {
            System.out.println(a.getOmschrijving()+"\t\t\t"+a.getAantalInKar()+"\t\t\t"+a.getVerkoopprijs());
        }
        System.out.println("********************************* \r\nBetaald(inc. Korting): €" + DBService.getInstance().getTotaalprijsMetKortingen());
    }
}
