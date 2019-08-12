package model.db;


import model.Artikel;

import java.util.*;

public class WinkelKarDB {

    private ArrayList<Artikel> artikels;

    public WinkelKarDB() {
        this.artikels = new ArrayList<Artikel>();
    }



    public String artikelsInWriteFormat(){
        String out = "";
        for (Artikel a:artikels) {
            out= out + a.toString()+"\r\n";
        }
        return  out;
    }





    public  void deleteArtikelWithIndex(int i){
        artikels.remove(i);
    }



    public ArrayList<Artikel> getAllArtikels(){
        return artikels;
    }


    public double getTotaalPrijs(){
        double totaal=0.0;
        for (Artikel a : artikels) {
            totaal = totaal + a.getVerkoopprijs();

        }
        return round(totaal,2);
    }



    public void addArtikelToKart(Artikel artikel){
        artikels.add(artikel);
    }

    public boolean isInWinkelkar(Artikel artikel){
        if(artikels.contains(artikel)){
            return true;
        }
        else return false;
    }

    public Artikel getArtikelMetCode(String code) throws DbExeption {
        for (Artikel a : artikels) {
            if (a.getCode().equals(code)) {
                return a;
            }
        }
        throw new DbExeption("Artikel niet in kar");
    }

    public boolean containsArtikelMetCode(String code){
        boolean bool=false;
        for (Artikel a:artikels) {
            if(a.getCode().equals(code)){
                bool=true;
                return bool;
            }
        }



        return bool;
    }


    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    @Override
    public String toString() {
        return "Artikels van Winkelmand{" +
                "artikels=" + artikels +
                '}';
    }
}



