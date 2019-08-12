package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private LocalDateTime now = LocalDateTime.now();
    private double totaal,korting,teBetalen;
    private String nowString;


    public Log(double totaal, double korting, double teBetalen) {
        this.setNowString();
        this.setTotaal(totaal);
        this.setKorting(korting);
        this.setTeBetalen(teBetalen);
    }




    public double getTotaal() {
        return totaal;
    }

    private void setTotaal(double totaal) {
        this.totaal = totaal;
    }

    public double getKorting() {
        return korting;
    }

    private void setKorting(double korting) {
        this.korting = korting;
    }

    public double getTeBetalen() {
        return teBetalen;
    }

    private void setTeBetalen(double teBetalen) {
        this.teBetalen = teBetalen;
    }

    public String getNowString() {
        return nowString;
    }

    private void setNowString() {
        this.nowString = dtf.format(now);
    }
}
