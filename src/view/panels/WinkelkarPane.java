package view.panels;


import controller.Controller;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Artikel;
import model.Observer;
import model.db.DBService;
import model.db.DbExeption;

import java.util.Optional;

public class WinkelkarPane extends GridPane implements Observer{
    private Button btnOK, btnCancel,btnStore,btnLoad,btnAfsluit,btnBetaald,btnAnul;
    private TableView table;
    private Controller controller;
    private TextField artikelScanField;
    private Double totaalBedrag=0.0;
    private Label totaalLable;
    private Label infoLable;
    private SimpleStringProperty simpleStringProperty,afsluitString;
//    private ObservableList<Artikel> gescandeArtikels = FXCollections.observableArrayList() ;



    public WinkelkarPane(Controller controller) {
        this.controller = controller;
        this.simpleStringProperty = new SimpleStringProperty("Nog Geen Artikelen Gescand");
        this.afsluitString = new SimpleStringProperty("");



        this.setPrefHeight(150);
        this.setPrefWidth(300);
        controller.registerDBObserver(this);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        //TEXTFIELD

        this.add(new Label("Winkelkar :"), 0, 0, 1, 1);
        artikelScanField = new TextField();
        this.add(artikelScanField, 1, 0, 1, 1);
        setEnterKeyAction(new EnterKeyListener());


        totaalLable = new Label();
        totaalLable.textProperty().bind(simpleStringProperty);
        this.add(totaalLable,4,5,1,1);

        infoLable = new Label("Dubbelklik een artikel om het te verwijderen.");
        this.add(infoLable,1,5,1,1);




//        totaalBedragLable = new Label("Totaal: ");
//        totaalBedragLable.textProperty().bind();
//        this.add(new Label("Totaal Bedrag= " + totaalBedrag ),0,6,1,1);



        btnCancel = new Button("Close");
        this.add(btnCancel, 0, 2, 1, 1);
        setCancelAction(new CancelListener());

        btnOK = new Button("Scan");
        btnOK.isDefaultButton();
        this.add(btnOK, 1, 2, 1, 1);
        setSaveAction(new SaveListener());

        btnStore = new Button("Store");
        btnStore.isDefaultButton();
        this.add(btnStore, 2, 2, 1, 1);
        setStoreAction(new StoreListener());
        btnLoad = new Button("Load");
        btnLoad.isDefaultButton();
        this.add(btnLoad, 3, 2, 1, 1);
        setLoadAction(new LoadListener());

        btnAfsluit = new Button("Afsluit");
        btnAfsluit.isDefaultButton();
        this.add(btnAfsluit, 4, 2, 1, 1);
        setAfsluitAction(new AfsluitListener());

        btnBetaald = new Button("Betaald");
        btnBetaald.isDefaultButton();
        this.add(btnBetaald, 5, 2, 1, 1);
        setBetaaldAction(new BetaaldListener());

        btnAnul = new Button("Anuleer");
        btnAnul.isDefaultButton();
        this.add(btnAnul, 6, 2, 1, 1);
        setAnuleerAction(new AnuleerListener());






        //TABLE
        //BLIJKBAAR ALLEEN ARTIKEL EN PRIJS DIT NOG AANPASSEN VOOR FINAL PUSH
        table = new TableView<>();
        table.setPrefWidth(REMAINING);
        TableColumn codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("Code"));
        table.getColumns().add(codeCol);

        TableColumn omschrijvingCol = new TableColumn<>("omschrijving");
        omschrijvingCol.setCellValueFactory(new PropertyValueFactory("omschrijving"));
        table.getColumns().add(omschrijvingCol);

        TableColumn artikelGroepCol = new TableColumn<>("artikelGroep");
        artikelGroepCol.setCellValueFactory(new PropertyValueFactory("artikelGroep"));
        table.getColumns().add(artikelGroepCol);

        TableColumn verkoopprijsCol = new TableColumn<>("verkoopprijs");
        verkoopprijsCol.setCellValueFactory(new PropertyValueFactory("verkoopprijs"));
        table.getColumns().add(verkoopprijsCol);

        TableColumn voorraadCol = new TableColumn<>("voorraad");
        voorraadCol.setCellValueFactory(new PropertyValueFactory("voorraad"));
        table.getColumns().add(voorraadCol);
        setKlickOnTableAction(new KlickOnTableListener());

        this.add(table, 0, 3, 5, 2);
        Label label = new Label("");
        label.textProperty().bind(afsluitString);
        this.add(label,0,6,1,1);


        ObservableList<Artikel> data = FXCollections.observableArrayList(controller.getWinkelKarArtikels());

        table.setItems(data);    }




    @Override
    public void update() {
        ObservableList<Artikel> data = FXCollections.observableArrayList(controller.getWinkelKarArtikels());
        table.setItems(data);

        totaalBedrag = round( controller.getTotaalMetKortingen(),2);
        simpleStringProperty.setValue("Totaal= €"+totaalBedrag.toString());
        if(DBService.getInstance().getKorting() != null){
            simpleStringProperty.setValue("Totaal= €"+totaalBedrag.toString()+" (Met korting)");
        }

    }

    class SaveListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            Artikel artikel = null;
            ScanArtikel();
        }


        }


    class CancelListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            final Node source = (Node) e.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            Platform.exit();

        }
    }

    class EnterKeyListener implements EventHandler<KeyEvent>{

        @Override
        public void handle(KeyEvent event) {
            if (event.getCode().equals(KeyCode.ENTER))
            {
                Artikel artikel = null;
                ScanArtikel();


            }
        }



    }

    class KlickOnTableListener implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    int n = table.getSelectionModel().getFocusedIndex();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to delete this Item: \n" + "\n code= " +controller.getWinkelKarArtikels().get(n).getCode()+"\n\r plaats= "+(n+1), ButtonType.CLOSE,ButtonType.APPLY);
                    alert.setTitle("DELETE");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.APPLY){
                        controller.getWinkelKarArtikels().get(n).verlaagAantalInKar();
                        controller.removeWithIndexFromWinkelkar(n);
                        DBService.getInstance().notifyObservers();
                    } else {
                        alert.close();
                    }



                }
            }
        }

    }

    class StoreListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            try {
                controller.storeWinkelkar();
                update();
                System.out.println("Winkelkar On Honld");
            } catch (DbExeption dbExeption) {
                System.out.println(dbExeption.getMessage());            }
                   }


    }


    class LoadListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            try {
                controller.loadWinkelkar();
                update();
                System.out.println("Winkelkar geladen");
            } catch (DbExeption dbExeption) {
                Alert alert = new Alert(Alert.AlertType.ERROR,dbExeption.getMessage(),ButtonType.CLOSE);
                alert.showAndWait();
                System.out.println(dbExeption.getMessage());;
            }
            catch (NullPointerException nullEx){
                Alert alert = new Alert(Alert.AlertType.ERROR,"Geen Winkelkar on hold",ButtonType.CLOSE);
                alert.showAndWait();
                System.out.println(nullEx.getMessage());;
            }

        }


    }

    class AfsluitListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            try {
                controller.setAfsluitString();
                afsluitString.setValue(controller.getAfsluitString());
                controller.notifyObservers();
            } catch (Exception dbExeption) {
                Alert alert = new Alert(Alert.AlertType.ERROR,dbExeption.getMessage(),ButtonType.CLOSE);
                alert.showAndWait();
                System.out.println(dbExeption.getMessage());;
            }

        }


    }


    class BetaaldListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            try {
               controller.betaalWinkelkar();
            } catch (Exception dbExeption) {
                Alert alert = new Alert(Alert.AlertType.ERROR,dbExeption.getMessage(),ButtonType.CLOSE);
                alert.showAndWait();
                System.out.println(dbExeption.getMessage());;
            }

        }


    }


    class AnuleerListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            try {
               controller.anulWinkelkar();
            } catch (Exception dbExeption) {
                Alert alert = new Alert(Alert.AlertType.ERROR,dbExeption.getMessage(),ButtonType.CLOSE);
                alert.showAndWait();
                System.out.println(dbExeption.getMessage());;
            }

        }


    }



    private void ScanArtikel() {
        Artikel artikel;
        try {
            artikel = controller.getArtikelWithCode(artikelScanField.getText());
            artikel.verhoogAantalInKar();
            controller.addArtikelToWinkelKar(artikel);


            this.update();

        } catch (DbExeption dbExeption) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION,dbExeption.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }

    public void setSaveAction(EventHandler<ActionEvent> saveAction) {
        btnOK.setOnAction(saveAction);
    }
    public void setEnterKeyAction(EventHandler<KeyEvent> enterKeyAction){ artikelScanField.setOnKeyPressed(enterKeyAction);}
    public void setCancelAction(EventHandler<ActionEvent> cancelAction) {
        btnCancel.setOnAction(cancelAction);
    }
    public void setKlickOnTableAction(EventHandler<MouseEvent> mouseEvent){table.setOnMouseClicked(mouseEvent);}
    public void setStoreAction(EventHandler<ActionEvent> storeAction) {
        btnStore.setOnAction(storeAction);
    }
    public void setAfsluitAction(EventHandler<ActionEvent> storeAction) {
        btnAfsluit.setOnAction(storeAction);
    }
    public void setBetaaldAction(EventHandler<ActionEvent> storeAction) {
        btnBetaald.setOnAction(storeAction);
    }

    public void setAnuleerAction(EventHandler<ActionEvent> storeAction) {
        btnAnul.setOnAction(storeAction);
    }


    public void setLoadAction(EventHandler<ActionEvent> loadAction) {btnLoad.setOnAction(loadAction);
    }



    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


}
