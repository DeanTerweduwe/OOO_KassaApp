package model.db;

import model.Artikel;
import model.Category;
import model.Observer;
import model.Subject;


import java.util.ArrayList;
import java.util.HashMap;


public class DBService implements Subject {

    private volatile static DBService uniqueInstance;
    private ArtikelDBTekst artikelDBTekst;

    private ArrayList<Observer> observers;

    private DBService () {
        this.artikelDBTekst = new ArtikelDBTekst();
        this.observers = new ArrayList<>();
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
       return artikelDBTekst.getAllArtikels();
    }

    public ArrayList<Artikel> getAllArtikelsTxtArraylist(){
        return artikelDBTekst.getAllArtikelsArrayList();
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
