package model;

public class DrempelKorting implements Korting{

        private double persentage;
        private double drempelPrijs;

    public DrempelKorting(double persentage,double drempelPrijs) {
        this.persentage = persentage;
    }


    @Override
    public Double getPersentage() {
    return persentage;
    }

    @Override
    public void setPersentage(double persentage) {
    this.setPersentage(persentage);
        }

    public double getDrempelPrijs() {
        return drempelPrijs;
    }

    public void setDrempelPrijs(double drempelPrijs) {
        this.drempelPrijs = drempelPrijs;
    }
}
