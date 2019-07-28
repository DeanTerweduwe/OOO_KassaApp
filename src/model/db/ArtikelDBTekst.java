package model.db;

import model.Artikel;
import model.ArtikelGroep;

import java.io.*;
import java.util.*;

public class ArtikelDBTekst implements LoadSave {
    private HashMap artikels;
    String tekstFilePath= "database_tekst/artikel.txt";
    File file = new File(tekstFilePath);




    public ArtikelDBTekst() {
        this.artikels = new HashMap<String,Artikel>();
        this.loadArtikels();
    }


    public void loadArtikels() {
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(tekstFilePath));
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length >= 5) {
                    Artikel artikel = new Artikel(parts[0], parts[1], ArtikelGroep.valueOf(parts[2]), Double.parseDouble(parts[3]), Integer.parseInt(parts[4]));
                    artikels.put(parts[0], artikel);
                    System.out.println("Tekst artikel met code: " + artikel.getCode() +" geladen");
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

    public String artikelsInWriteFormat(){
        String out = "";
        for (Artikel a:getAllArtikelsArrayList()) {
            out= out + a.toString()+"\r\n";
        }
    return  out;
    }



    public void saveArtikels(){

        File file =new File("database_tekst/artikel.txt");
        file.delete();
        File newFile=new File("database_tekst/artikel.txt");

        try {
            FileWriter f2 = new FileWriter(newFile, false);
            f2.write(artikelsInWriteFormat());
            f2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }





    public HashMap<String,Artikel> getAllArtikels(){
        return artikels;
    }

    public ArrayList<Artikel> getAllArtikelsArrayList(){


        ArrayList<Artikel> list = new ArrayList<Artikel>(artikels.values());
        Comparator<Artikel> compareByOm = (Artikel a1, Artikel a2) -> a1.getOmschrijving().compareTo( a2.getOmschrijving() );
        Collections.sort(list, compareByOm);
        return list;
    }

    @Override
    public Artikel getArtikelWithCode(String code) throws DbExeption {
        Artikel artikel = (Artikel) artikels.get(code);
        if (code.isEmpty()){
            throw new DbExeption("Code Is Leeg");
        }
        if(artikels.containsKey(code)){
            return artikel;

        }
        else{
            throw new DbExeption("Niet Bestaande Code");
        }
    }


    @Override
    public String toString() {
        return "Artikels van tekst{" +
                "artikels=" + artikels +
                '}';
    }
}
