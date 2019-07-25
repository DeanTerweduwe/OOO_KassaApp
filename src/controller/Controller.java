package controller;

import model.Artikel;
import model.ArtikelGroep;
import model.DataType;
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



    public static ArrayList<String> getAllDatatypes(){
        ArrayList<String> groepen = new ArrayList<>();
        for (DataType type:DataType.values()) {
            groepen.add(type.toString());
        }
        return groepen;
    }

}
