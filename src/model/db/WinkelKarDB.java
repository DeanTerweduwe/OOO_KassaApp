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

    @Override
    public String toString() {
        return "Artikels van Winkelmand{" +
                "artikels=" + artikels +
                '}';
    }
}



