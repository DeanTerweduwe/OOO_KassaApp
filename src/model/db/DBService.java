package model.db;

import model.Artikel;
import model.Category;
import model.Observer;
import model.Subject;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;


public class DBService implements Subject {

    private volatile static DBService uniqueInstance;
    private LoadSave loadSaveDatabase;

    private ArrayList<Observer> observers;

    private DBService () {
        try {
            this.setLoadSaveDatabase();
            this.observers = new ArrayList<>();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static DBService getInstance(){
        if (uniqueInstance == null) {
            synchronized (DBService.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new DBService();
                }
            }
        }
        return uniqueInstance;
    }



    public HashMap<String, Artikel> getAllArtikels(){
       return loadSaveDatabase.getAllArtikels();
    }

    public ArrayList<Artikel> getAllArtikelsTxtArraylist(){
        return loadSaveDatabase.getAllArtikelsArrayList();
    }

    public void setLoadSaveDatabase() throws IOException {
        Properties properties = new Properties();
        InputStream is = new FileInputStream("kassa.properties");
        properties.load(is);
        String stringTypeDb = properties.getProperty("type");
        if (stringTypeDb.equals("excel")){
            this.loadSaveDatabase = new ArtikelDBExcel();
        }
        else if (stringTypeDb.equals("tekst")){
            this.loadSaveDatabase = new ArtikelDBTekst();
        }
        else{
           throw new IllegalArgumentException("not the right property");
        }
    }



    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }


    @Override
    public void notifyObservers() {
        for(Observer observer : observers) {
            observer.update();
        }
    }
}
