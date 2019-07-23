package model;

public class Artikel {

    private String code;
    private String omschrijving;
    private ArtikelGroep artikelGroep;
    private double verkoopprijs;
    private int vooraad;

    public Artikel(String code, String omschrijving, ArtikelGroep artikelGroep, double verkoopprijs, int vooraad) {
        this.setCode(code);
        this.setOmschrijving(omschrijving);
        this.setArtikelGroep(artikelGroep);
        this.setVerkoopprijs(verkoopprijs);
        this.setVooraad(vooraad);
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

    public int getVooraad() {
        return vooraad;
    }

    public void setVooraad(int vooraad) {
        this.vooraad = vooraad;
    }
}
