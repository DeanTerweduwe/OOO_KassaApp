package model;

public class GroepKorting implements Korting {

    private double persentage;
    private ArtikelGroep artikelGroep;

    public GroepKorting(double persentage,ArtikelGroep artikelGroep) {
        setPersentage(persentage);
        setArtikelGroep(artikelGroep);
    }


    @Override
    public Double getPersentage() {
        return persentage;
    }

    @Override
    public void setPersentage(double persentage) {

    this.persentage=persentage;
    }

    public ArtikelGroep getArtikelGroep() {
        return artikelGroep;
    }

    public void setArtikelGroep(ArtikelGroep artikelGroep) {
        this.artikelGroep = artikelGroep;
    }
}
