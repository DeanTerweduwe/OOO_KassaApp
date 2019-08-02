package model;

public class DuursteKorting implements Korting {

   private double persentage;

    public DuursteKorting( double persentage) {
        setPersentage(persentage);
    }


    @Override
    public Double getPersentage() {
        return persentage;
    }

    @Override
    public void setPersentage(double persentage) {
    this.persentage=persentage;
    }
}
