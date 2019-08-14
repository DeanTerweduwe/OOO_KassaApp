package model.db;

import model.*;
import model.KassaBonDecorator.KassaBon;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;


public class DBService implements Subject {

    private volatile static DBService uniqueInstance;
    private LoadSave loadSaveDatabase;
    private WinkelKarDB winkelKarDB;
    private WinkelKarDB onHoldWinkelKar;
    private ArrayList<Observer> observers;
    private Korting korting;
    private String afluitString;
    private ArrayList<Log> log;
    private String testLog;

    private DBService () {
        try {
            this.setLoadSaveDatabase();
            this.winkelKarDB=new WinkelKarDB();
            this.observers = new ArrayList<>();
            this.afluitString="";
            this.log= new ArrayList<>();
            this.testLog="";
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (DbExeption e){
            System.out.println(e.getMessage());
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


    public ArrayList<Artikel> getWinkelkarArtikels(){return winkelKarDB.getAllArtikels();}

    public void addArtikelToWinkelKar(Artikel artikel){winkelKarDB.addArtikelToKart(artikel);
        notifyObservers();
    }

    public void removeWithIndexFromWinkelkar(int i){
        winkelKarDB.deleteArtikelWithIndex(i);
    }

    public HashMap<String, Artikel> getAllArtikels(){
       return loadSaveDatabase.getAllArtikels();
    }

    public ArrayList<Artikel> getAllArtikelsTxtArraylist(){
        return loadSaveDatabase.getAllArtikelsArrayList();
    }

    public Artikel getArtikelMetCode(String code) throws DbExeption {return loadSaveDatabase.getArtikelWithCode(code);}

    public void setLoadSaveDatabase() throws IOException, DbExeption {

        LoadSaveFactory factory = new LoadSaveFactory();
        this.loadSaveDatabase = factory.createLoadSave();

        //ZONDER FACTORY REFACTORED
//        Properties properties = new Properties();
//        InputStream is = new FileInputStream("kassa.properties");
//        properties.load(is);
//        String stringTypeDb = properties.getProperty("type");
//        if (stringTypeDb.equals("excel")){
//            this.loadSaveDatabase = new ArtikelDBExcel();
//        }
//        else if (stringTypeDb.equals("tekst")){
//            this.loadSaveDatabase = new ArtikelDBTekst();
//        }
//        else{
//           throw new IllegalArgumentException("not the right property");
//        }
    }


    public void setKorting(Korting korting){
    this.korting = korting;
    }

    public Korting getKorting() {
        return korting;
    }

    public void setAfluitString(){
        afluitString = this.maakAfluitString();
    }

    public void anulWinkelkar(){
        this.winkelKarDB = new WinkelKarDB();
        notifyObservers();
    }

    public void betaalWinkelkar(){
        KassaBon kassaBon = new KassaBon(winkelKarDB);
        kassaBon.drukAf();
        for (Artikel a:winkelKarDB.getAllArtikels() ) {
            a.setVoorraad(a.getVoorraad()-a.getAantalInKar());
            a.setAantalInKar(0);
        }
        loadSaveDatabase.saveArtikels();
        loadSaveDatabase.loadArtikels();
        this.logBetaling();
        notifyObservers();
        this.anulWinkelkar();
    }

    public void storeWinkelkar() throws DbExeption {
        onHoldWinkelKar = new WinkelKarDB();
        Artikel tempArt;
    for (Artikel artikel: winkelKarDB.getAllArtikels() ) {
        if(onHoldWinkelKar.containsArtikelMetCode(artikel.getCode())){
            System.out.println("artikel zit in winkelkar");
            onHoldWinkelKar.addArtikelToKart(onHoldWinkelKar.getArtikelMetCode(artikel.getCode()));
        }
        else {tempArt = new Artikel(artikel.getCode(),artikel.getOmschrijving(),artikel.getArtikelGroep(),artikel.getVerkoopprijs(),artikel.getVoorraad());
        tempArt.setAantalInKar(artikel.getAantalInKar());
        onHoldWinkelKar.addArtikelToKart(tempArt);}
    }

    winkelKarDB = new WinkelKarDB();
    for (   Artikel artikel     :    loadSaveDatabase.getAllArtikelsArrayList()) {
        artikel.setAantalInKar(0);
    }

    for (Artikel a : onHoldWinkelKar.getAllArtikels()    ) {
        System.out.println(a.getAantalInKar());
    }
        notifyObservers();
}

public void reloadStoredWinkelkar() throws DbExeption {
        winkelKarDB = new WinkelKarDB();
    for (Artikel a: onHoldWinkelKar.getAllArtikels()) {

        if(winkelKarDB.containsArtikelMetCode(a.getCode())){
            System.out.println("artikel zit in winkelkar");
            winkelKarDB.addArtikelToKart(DBService.getInstance().getArtikelMetCode(a.getCode()));
        }
        else {
            DBService.getInstance().getArtikelMetCode(a.getCode()).setAantalInKar(a.getAantalInKar());
            winkelKarDB.addArtikelToKart(DBService.getInstance().getArtikelMetCode(a.getCode()));

        }
    }


        onHoldWinkelKar=new WinkelKarDB();
        notifyObservers();

}

public double getTotaalprijs(){
        return round(winkelKarDB.getTotaalPrijs(),2);
}

public double getTotaalprijsMetKortingen() {
    double totaal = 0.0;
    if(korting != null) {
        Korting korting = this.korting;


        if (korting instanceof GroepKorting) {
            for (Artikel a : winkelKarDB.getAllArtikels()) {
                if(a.getArtikelGroep() == ((GroepKorting) korting).getArtikelGroep()){
                    totaal = totaal + (a.getVerkoopprijs()-(a.getVerkoopprijs()*(korting.getPersentage()/100)));
                }
                else totaal = totaal + a.getVerkoopprijs();
            }

        }
        if (korting instanceof DrempelKorting) {
            for (Artikel a : winkelKarDB.getAllArtikels()) {
                totaal = totaal + a.getVerkoopprijs();
            }
            if(totaal>=((DrempelKorting) korting).getDrempelPrijs()){
                totaal= totaal - (totaal*(korting.getPersentage()/100));
            }
        }
        if (korting instanceof DuursteKorting) {
            Artikel duurste=winkelKarDB.getAllArtikels().get(0);
            for (Artikel a : winkelKarDB.getAllArtikels()) {
                totaal = totaal + a.getVerkoopprijs();
                if (a.getVerkoopprijs()>duurste.getVerkoopprijs()){
                    duurste = a;
                }
            }
            totaal = totaal -(duurste.getVerkoopprijs() *(korting.getPersentage()/100));
        }

        return round(totaal,2);
    }
    else {
        for (Artikel a : winkelKarDB.getAllArtikels()) {
            totaal = totaal + a.getVerkoopprijs();
        }
    }
    return round(totaal,2);
}


    public String maakAfluitString(){
        String out ="Totaal te betalen: €";
        out = out + this.getTotaalprijs()+"\n\r";
        out=out + "Na korting: €"+this.getTotaalprijsMetKortingen();



        return out;
    }

    public String getAfluitString(){
        return this.afluitString;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    private String getLogString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String logString = dtf.format(now)+" - Totaal:" +getTotaalprijs() +" - Korting:"+ (getTotaalprijs()-getTotaalprijsMetKortingen())+" - Te Betalen:"+getTotaalprijsMetKortingen();
        return logString;
    }

    private void logBetaling(){
//        testLog = testLog + "\r\n"+ getLogString();
        Log log1 = new Log(getTotaalprijs(),(getTotaalprijs()-getTotaalprijsMetKortingen()),getTotaalprijsMetKortingen());
        log.add(log1);

    }
    public ArrayList<Log> getLog(){
        return this.log;
    }

    public WinkelKarDB getWinkelKar() {
        return winkelKarDB;
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
