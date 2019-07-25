package controller;

import model.Artikel;
import model.ArtikelGroep;
import model.Observer;
import model.db.DBService;

import java.util.ArrayList;
import java.util.HashMap;

public class Controller {





    public Controller() {
    }



    public static HashMap<String, Artikel> getAllArtikelsTxt() {
        return DBService.getInstance().getAllArtikels();
    }
    public static ArrayList<Artikel> getAllArtikelsTxtArray(){
       return DBService.getInstance().getAllArtikelsTxtArraylist();
    }
    public static void registerDBObserver(Observer o) {
        DBService.getInstance().registerObserver(o);
    }

    public ArrayList<String> getAllDatatypes(){
        ArrayList<String> groepen = new ArrayList<>();
        for (ArtikelGroep art:ArtikelGroep.values()) {
            groepen.add(art.toString());
        }
        return groepen;
    }

}
