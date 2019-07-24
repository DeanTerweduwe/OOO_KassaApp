package model.db;

import model.Artikel;

import java.util.ArrayList;
import java.util.HashMap;

public interface LoadSave {

    void loadArtikels();
    void saveArtikels();
    HashMap<String,Artikel> getAllArtikels();
    ArrayList<Artikel> getAllArtikelsArrayList();



}
