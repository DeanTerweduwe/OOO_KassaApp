package model.db;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.Artikel;
import model.ArtikelGroep;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ArtikelDBExcel extends ExcelPlugin implements LoadSave{

    private HashMap artikels ;
    String excelFilePath= "database_excel/artikel.xls";
    File file = new File(excelFilePath);




    public ArtikelDBExcel() {
        this.artikels = new HashMap<String,Artikel>();
        this.loadArtikels();
    }










    @Override
    public void loadArtikels() {
        ArrayList<ArrayList<String>> stringList = null;
        try {
            stringList = super.read(file);

        for (ArrayList<String> stringArrayList :stringList ) {
            Artikel artikel=new  Artikel(stringArrayList.get(0),stringArrayList.get(1), ArtikelGroep.valueOf(stringArrayList.get(2)),Double.parseDouble(stringArrayList.get(3)),Integer.parseInt(stringArrayList.get(4)));
           artikels.put(stringArrayList.get(0),artikel);
            System.out.println("Excel artikel met code: " + artikel.getCode() +" geladen");
        }

        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ArrayList<ArrayList<String>> getArtikelsStringArray(){
        ArrayList<ArrayList<String>> a2 = new ArrayList<>();
        ArrayList<String> a1 = new ArrayList<>();
        ArrayList<Artikel> list = new ArrayList<Artikel>(artikels.values());
        for (Artikel a: list) {
            a1.add(a.getCode());

            a1.add(a.getOmschrijving());
            a1.add(a.getArtikelGroep().toString());
            a1.add(String.valueOf(a.getVerkoopprijs()));
            a1.add(String.valueOf(a.getVoorraad()));
            a2.add(a1);
            a1 = new ArrayList<>();

        }
        return a2;
    }


    @Override
    public void saveArtikels() {
        try {
            super.write(file,getArtikelsStringArray());
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<String, Artikel> getAllArtikels() {
        return artikels;
    }

    @Override
    public ArrayList<Artikel> getAllArtikelsArrayList() {
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




}
