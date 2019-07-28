package model.db;

import jxl.read.biff.BiffException;
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

    @Override
    public void saveArtikels() {

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
