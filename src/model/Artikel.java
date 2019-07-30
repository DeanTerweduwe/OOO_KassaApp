package model;

public class Artikel {

    private String code;
    private String omschrijving;
    private ArtikelGroep artikelGroep;
    private double verkoopprijs;
    private int voorraad;
    private int aantalInKar;

    public Artikel(String code, String omschrijving, ArtikelGroep artikelGroep, double verkoopprijs, int voorraad) {
        this.setCode(code);
        this.setOmschrijving(omschrijving);
        this.setArtikelGroep(artikelGroep);
        this.setVerkoopprijs(verkoopprijs);
        this.setVoorraad(voorraad);
        aantalInKar=0;
    }

    public String getCode() {
        return code;
    }

    private void setCode(String code) {
        this.code = code;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public ArtikelGroep getArtikelGroep() {
        return artikelGroep;
    }


    public void setArtikelGroep(ArtikelGroep artikelGroep) {
        this.artikelGroep = artikelGroep;
    }

    public double getVerkoopprijs() {
        return verkoopprijs;
    }

    public void setVerkoopprijs(double verkoopprijs) {
        this.verkoopprijs = verkoopprijs;
    }

    public int getVoorraad() {
        return voorraad;
    }

    public void setVoorraad(int vooraad) {
        this.voorraad = vooraad;
    }

    public int getAantalInKar(){
        return aantalInKar;
    }

    public void verhoogAantalInKar(){
        aantalInKar++;
    }
    public void verlaagAantalInKar(){aantalInKar --;}

    public void setAantalInKar(int aantalInKar) {
        this.aantalInKar = aantalInKar;
    }

    @Override
    public String toString() {
        return code+","+omschrijving+","+artikelGroep+","+verkoopprijs+","+voorraad;
    }

}
