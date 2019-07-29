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





    public void addArtikelToKart(Artikel artikel){
        artikels.add(artikel);
    }


    @Override
    public String toString() {
        return "Artikels van Winkelmand{" +
                "artikels=" + artikels +
                '}';
    }
}



