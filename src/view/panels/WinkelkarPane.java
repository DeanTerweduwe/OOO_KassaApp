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
import model.db.DbExeption;

public class WinkelkarPane extends GridPane implements Observer{
    private Button btnOK, btnCancel;
    private TableView table;
    private Controller controller;
    private TextField artikelScanField;
    private Double totaalBedrag=0.0;
    private Label totaalLable;
    private SimpleStringProperty simpleStringProperty;
//    private ObservableList<Artikel> gescandeArtikels = FXCollections.observableArrayList() ;



    public WinkelkarPane(Controller controller) {
        this.controller = controller;
        this.simpleStringProperty = new SimpleStringProperty();



        this.setPrefHeight(150);
        this.setPrefWidth(300);
        controller.registerDBObserver(this);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        //TEXTFIELD

        this.add(new Label("Winkelkar:"), 0, 0, 1, 1);
        artikelScanField = new TextField();
        this.add(artikelScanField, 1, 0, 1, 1);
        setEnterKeyAction(new EnterKeyListener());


        totaalLable = new Label("Totaal= ");
        totaalLable.textProperty().bind(simpleStringProperty);
        this.add(totaalLable,1,6,1,1);


//        totaalBedragLable = new Label("Totaal: ");
//        totaalBedragLable.textProperty().bind();
//        this.add(new Label("Totaal Bedrag= " + totaalBedrag ),0,6,1,1);



        btnCancel = new Button("Cancel");
        this.add(btnCancel, 0, 2, 1, 1);
        setCancelAction(new CancelListener());

        btnOK = new Button("Scan");
        btnOK.isDefaultButton();
        this.add(btnOK, 1, 2, 1, 1);
        setSaveAction(new SaveListener());





        //TABLE
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

        this.add(table, 0, 3, 5, 3);

        ObservableList<Artikel> data = FXCollections.observableArrayList(controller.getWinkelKarArtikels());

        table.setItems(data);    }

    //		btnNew = new Button("New");
//		this.add(btnNew, 0, 11, 1, 1);
//		setNewAction(new NewListener());
//
//		table.setOnMouseClicked(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent mouseEvent) {
//				if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
//					if(mouseEvent.getClickCount() == 2){
//						int n = table.getSelectionModel().getFocusedIndex();
//						Stage stage = new Stage();
//						EditNewQuestionDetailPane newQuestionDetailPane = new EditNewQuestionDetailPane(controller
//								, controller.getAllQuestions().get(n));
//						newQuestionDetailPane.start(stage);
//						stage.show();
//					}
//				}
//			}
//		});
//	}
//


    @Override
    public void update() {
        ObservableList<Artikel> data = FXCollections.observableArrayList(controller.getWinkelKarArtikels());
        table.setItems(data);


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
            stage.close();
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


    private void ScanArtikel() {
        Artikel artikel;
        try {
            artikel = controller.getArtikelWithCode(artikelScanField.getText());
            controller.addArtikelToWinkelKar(artikel);
            totaalBedrag=totaalBedrag+artikel.getVerkoopprijs();
            simpleStringProperty.setValue("Totaal= €"+totaalBedrag.toString());
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




}
