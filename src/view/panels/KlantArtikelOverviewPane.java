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

public class KlantArtikelOverviewPane extends GridPane implements Observer {
    private TableView table;
    private Controller controller;
    private Double totaalBedrag=0.0;
    private Label totaalLable;
    private SimpleStringProperty simpleStringProperty;
    private ObservableList<Integer> aantallen=FXCollections.observableArrayList();
//    private ObservableList<Artikel> gescandeArtikels = FXCollections.observableArrayList() ;



    public KlantArtikelOverviewPane(Controller controller) {
        this.controller = controller;
        this.simpleStringProperty = new SimpleStringProperty();



        this.setPrefHeight(150);
        this.setPrefWidth(300);
        controller.registerDBObserver(this::update);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        //TEXTFIELD

        this.add(new Label("Winkelkar:"), 0, 0, 1, 1);


        totaalLable = new Label("Totaal= ");
        totaalLable.textProperty().bind(simpleStringProperty);
        this.add(totaalLable,1,5,1,1);


        //TABLE
        table = new TableView<>();
        table.setPrefWidth(REMAINING);
        TableColumn omschrijvingCol = new TableColumn<>("omschrijving");
        omschrijvingCol.setCellValueFactory(new PropertyValueFactory("omschrijving"));
        table.getColumns().add(omschrijvingCol);


        TableColumn verkoopprijsCol = new TableColumn<>("verkoopprijs");
        verkoopprijsCol.setCellValueFactory(new PropertyValueFactory("verkoopprijs"));
        table.getColumns().add(verkoopprijsCol);



        this.add(table, 0, 1, 5, 3);

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
        Double totaalTemp = 0.0;
        for (Artikel a: controller.getWinkelKarArtikels()) {
            totaalTemp=totaalTemp+a.getVerkoopprijs();
        }
        totaalBedrag = totaalTemp;
        simpleStringProperty.setValue("Totaal= €"+totaalBedrag.toString());


    }










}

