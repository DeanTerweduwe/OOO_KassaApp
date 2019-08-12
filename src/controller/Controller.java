package controller;

import model.*;
import model.db.DBService;
import model.db.DbExeption;

import java.util.ArrayList;
import java.util.HashMap;

public class Controller {
 String afsluit;




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

    public static void setKorting(Korting korting)  {
        DBService.getInstance().setKorting(korting);
    }

    public static ArrayList<String> getAllKortingTypes(){
        ArrayList<String> kortingen = new ArrayList<>();
        for (KortingsType type:KortingsType.values()) {
            kortingen.add(type.toString());
        }
    return kortingen;
    }
    public static ArrayList<Log> getLog(){
        return DBService.getInstance().getLog();
    }


    public static double getTotaalMetKortingen(){
        return DBService.getInstance().getTotaalprijsMetKortingen();

    }

    public static double getTotaal(){
       return DBService.getInstance().getTotaalprijs();
    }

    public static String getAfsluitString(){
        return DBService.getInstance().getAfluitString();
    }

    public static void setAfsluitString(){
        DBService.getInstance().setAfluitString();
    }

    public static ArrayList<String> getAllDatatypes(){
        ArrayList<String> types = new ArrayList<>();
        for (DataType type:DataType.values()) {
            types.add(type.toString());
        }
        return types;
    }

    public static ArrayList<String> getAllArtikelGroepen(){
        ArrayList<String> groepen = new ArrayList<>();
        for (ArtikelGroep artikelGroep:ArtikelGroep.values()) {
            groepen.add(artikelGroep.toString());
        }
        return groepen;
    }

    public static void anulWinkelkar(){
        DBService.getInstance().anulWinkelkar();
    }

    public static void betaalWinkelkar(){
        DBService.getInstance().betaalWinkelkar();
    }

    public static void notifyObservers(){
        DBService.getInstance().notifyObservers();
    }

}
