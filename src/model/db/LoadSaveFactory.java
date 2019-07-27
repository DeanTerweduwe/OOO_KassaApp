package model.db;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadSaveFactory {
    public LoadSave createLoadSave() throws DbExeption, IOException {
        LoadSave loadSaveDatabase = null;

        Properties properties = new Properties();
        InputStream is = new FileInputStream("kassa.properties");
        properties.load(is);
        String stringTypeDb = properties.getProperty("type");
        if (stringTypeDb.equals("excel")) {
            loadSaveDatabase = new ArtikelDBExcel();
        } else if (stringTypeDb.equals("tekst")) {
            loadSaveDatabase = new ArtikelDBTekst();
        } else {
            throw new DbExeption("not the right property");
        }
        return loadSaveDatabase;
    }
}
