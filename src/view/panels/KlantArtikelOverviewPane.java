package view.panels;


        import controller.Controller;
        import javafx.beans.InvalidationListener;
        import javafx.beans.property.SimpleStringProperty;
        import javafx.beans.property.StringProperty;
        import javafx.beans.value.ChangeListener;
        import javafx.beans.value.ObservableValue;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.geometry.Insets;
        import javafx.scene.Node;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.input.KeyCode;
        import javafx.scene.input.KeyEvent;
        import javafx.scene.layout.GridPane;
        import javafx.stage.Stage;
        import model.Artikel;
        import model.Observer;
        import model.db.DBService;
        import model.db.DbExeption;

        import java.util.HashMap;
        import java.util.HashSet;
        import java.util.Set;

public class KlantArtikelOverviewPane extends GridPane implements Observer {
    private TableView table;
    private Controller controller;
    private Double totaalBedrag=0.0;
    private Label totaalLable;
    private SimpleStringProperty simpleStringProperty,afsluitString;
    private ObservableList<Artikel> noDuplicates=FXCollections.observableArrayList();
//    private ObservableList<Artikel> gescandeArtikels = FXCollections.observableArrayList() ;



    public KlantArtikelOverviewPane(Controller controller) {
        this.controller = controller;
        this.simpleStringProperty = new SimpleStringProperty();
        this.afsluitString=new SimpleStringProperty();


        this.setPrefHeight(150);
        this.setPrefWidth(300);
        controller.registerDBObserver(this::update);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        this.add(new Label("Winkelkar:"), 0, 0, 1, 1);


        totaalLable = new Label("Totaal= ");
        totaalLable.textProperty().bind(simpleStringProperty);
        this.add(totaalLable,1,5,1,1);


        Label label = new Label("");
        label.textProperty().bind(afsluitString);
        this.add(label,0,6,1,1);


        //TABLE
        table = new TableView<>();
        table.setPrefWidth(REMAINING);
        TableColumn omschrijvingCol = new TableColumn<>("omschrijving");
        omschrijvingCol.setCellValueFactory(new PropertyValueFactory("omschrijving"));
        table.getColumns().add(omschrijvingCol);


        TableColumn verkoopprijsCol = new TableColumn<>("verkoopprijs");
        verkoopprijsCol.setCellValueFactory(new PropertyValueFactory("verkoopprijs"));
        table.getColumns().add(verkoopprijsCol);

        TableColumn aantalCol = new TableColumn<>("aantalCol");
        aantalCol.setCellValueFactory(new PropertyValueFactory("aantalInKar"));
        table.getColumns().add(aantalCol);



        this.add(table, 0, 1, 5, 3);

        ObservableList<Artikel> data = FXCollections.observableArrayList(controller.getWinkelKarArtikels());

        table.setItems(data);





    }




    @Override
    public void update() {
        ObservableList<Artikel> data = FXCollections.observableArrayList(controller.getWinkelKarArtikels());
        Set<Artikel> set = new HashSet<>(data);
        data.removeAll();
        data.setAll(set);
        table.refresh();
        table.setItems(data);
        Double totaalTemp = 0.0;
        totaalBedrag = controller.getTotaalMetKortingen();
        simpleStringProperty.setValue("Totaal= €"+totaalBedrag.toString());
        if(DBService.getInstance().getKortingen().size() != 0){
            simpleStringProperty.setValue("Totaal= €"+totaalBedrag.toString()+" (Met korting)");
        }

        afsluitString.setValue(controller.getAfsluitString());


    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }












}

