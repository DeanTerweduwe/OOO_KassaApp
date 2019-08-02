package controller;

import model.*;
import model.db.DBService;
import model.db.DbExeption;

import java.util.ArrayList;
import java.util.HashMap;

public class Controller {





    public Controller() {
    }



    public static HashMap<String, Artikel> getAllArtikels() {
        return DBService.getInstance().getAllArtikels();
    }
    public static ArrayList<Artikel> getAllArtikelsArray(){
       return DBService.getInstance().getAllArtikelsTxtArraylist();
    }
    public static Artikel getArtikelWithCode(String code) throws DbExeption {return DBService.getInstance().getArtikelMetCode(code);}

    public static void registerDBObserver(Observer o) {
        DBService.getInstance().registerObserver(o);
    }

    public static  ArrayList<Artikel> getWinkelKarArtikels(){return DBService.getInstance().getWinkelkarArtikels();}

    public static void addArtikelToWinkelKar(Artikel artikel){DBService.getInstance().addArtikelToWinkelKar(artikel);}
    public static void removeWithIndexFromWinkelkar(int i){
        DBService.getInstance().removeWithIndexFromWinkelkar(i);
    }

    public static void storeWinkelkar() throws DbExeption {
        DBService.getInstance().storeWinkelkar();
    }

    public static void loadWinkelkar() throws DbExeption {
        DBService.getInstance().reloadStoredWinkelkar();
    }

    public static void addKorting(Korting korting)  {
        DBService.getInstance().addKorting(korting);
    }

    public static ArrayList<String> getAllKortingTypes(){
        ArrayList<String> kortingen = new ArrayList<>();
        for (KortingsType type:KortingsType.values()) {
            kortingen.add(type.toString());
        }
    return kortingen;
    }

    public static ArrayList<String> getAllDatatypes(){
        ArrayList<String> groepen = new ArrayList<>();
        for (DataType type:DataType.values()) {
            groepen.add(type.toString());
        }
        return groepen;
    }

    public static void notifyObservers(){
        DBService.getInstance().notifyObservers();
    }

}
