package model.KassaBonDecorator;

import model.db.DBService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TijdDecorator extends KassaBonDecorator{
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private LocalDateTime now = LocalDateTime.now();
    private String nowString;

    public TijdDecorator(KassaBon decoKassaBon) {
        super(decoKassaBon, null);
        this.nowString = dtf.format(now);


    }

    @Override
    public void drukAf() {
        decoKassaBon.drukAf();
        System.out.println("********************************* \n"+  nowString +"\n********************************* ");
    }

}
