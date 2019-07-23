package model.db;

import model.Artikel;
import model.ArtikelGroep;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ArtikelDBTekst {
    private HashMap artikels;
    String tekstFilePath= "database_tekst/artikel.txt";
    File file = new File(tekstFilePath);




    public ArtikelDBTekst() {
        this.artikels = new HashMap<String,Artikel>();
        this.loadArtikels();
    }

    private void loadArtikels() {
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(tekstFilePath));
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length >= 5) {
                    Artikel artikel = new Artikel(parts[0], parts[1], ArtikelGroep.valueOf(parts[2]), Double.parseDouble(parts[3]), Integer.parseInt(parts[4]));
                    artikels.put(parts[0], artikel);
                    System.out.println("artikel met code: " + artikel.getCode() +" geladen");
                } else {
                    System.out.println("line not right format: " + line);
                }
            }
            reader.close();
        }
     catch (FileNotFoundException e) {
        e.printStackTrace();
    }
     catch (Exception e) {
        e.printStackTrace();
    }

    }


    public HashMap<String,Artikel> getAllArtikels(){
        return artikels;
    }

    public ArrayList<Artikel> getAllArtikelsArrayList(){
        ArrayList<Artikel> list = new ArrayList<Artikel>(artikels.values());
        return list;
    }


    @Override
    public String toString() {
        return "Artikels van tekst{" +
                "artikels=" + artikels +
                '}';
    }
}
