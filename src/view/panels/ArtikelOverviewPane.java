package view.panels;


import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.Artikel;
import model.Observer;


public class ArtikelOverviewPane extends GridPane implements Observer {

	private TableView table;
	private Controller controller;

	public ArtikelOverviewPane(Controller controller) {
		this.controller = controller;
		controller.registerDBObserver(this);
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setVgap(5);
		this.setHgap(5);

		this.add(new Label("Artikels:"), 0, 0, 1, 1);

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

		this.add(table, 0, 1, 5, 10);
		ObservableList<Artikel> data = FXCollections.observableArrayList(controller.getAllArtikelsArray());

		table.setItems(data);
	}

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
		ObservableList<Artikel> data = FXCollections.observableArrayList(controller.getAllArtikels().values());
		table.setItems(data);
	}
//
//	class NewListener implements EventHandler<ActionEvent> {
//		@Override
//		public void handle(ActionEvent e) {
//			Stage stage = new Stage();
//			NonEditNewQuestionDetailPane newQuestionDetailPane = new NonEditNewQuestionDetailPane(controller);
//			newQuestionDetailPane.start(stage);
//			stage.show();
//
//		}
//	}

//	public void setNewAction(EventHandler<ActionEvent> newAction) {
//		btnNew.setOnAction(newAction);
//	}
//
//	public void setEditAction(EventHandler<MouseEvent> editAction) {
//		table.setOnMouseClicked(editAction);
//	}

}



//	private TableView table;
//	private Controller controller;
//
//	public ArtikelOverviewPane(Controller controller) {
//		this.controller = controller;
//		controller.registerDBObserver(this);
//		this.setPadding(new Insets(5, 5, 5, 5));
//        this.setVgap(5);
//        this.setHgap(5);
//
//		this.add(new Label("Artikels:"), 0, 0, 1, 1);
//		table = new TableView<Artikel>();
//		table.setPrefWidth(REMAINING);
//        TableColumn codeCol = new TableColumn<>("code");
//        codeCol.setCellValueFactory(new PropertyValueFactory<Category,String>("code"));
//        table.getColumns().add(codeCol);
//
//        TableColumn descriptionCol = new TableColumn<>("omschrijving");
//        descriptionCol.setCellValueFactory(new PropertyValueFactory<Category,String>("omschrijving"));
//        table.getColumns().add(descriptionCol);
//
//		TableColumn groepCol = new TableColumn<>("groep");
//		groepCol.setCellValueFactory(new PropertyValueFactory<Category,String>("groep"));
//		table.getColumns().add(groepCol);
//
//		TableColumn priceCol = new TableColumn<>("prijs");
//		priceCol.setCellValueFactory(new PropertyValueFactory<Category,String>("prijs"));
//		table.getColumns().add(priceCol);
//
//		TableColumn voorraadCol = new TableColumn<>("voorraad");
//		voorraadCol.setCellValueFactory(new PropertyValueFactory<Category,String>("voorraad"));
//		table.getColumns().add(voorraadCol);
//
//		this.add(table, 0, 1, 2, 6);
//		ObservableList<Artikel> data = FXCollections.observableArrayList(controller.getAllArtikels().values());
//		table.setItems(data);
////		btnNew = new Button("New");
////		this.add(btnNew, 0, 11, 1, 1);
////		setNewAction(new NewListener());
////		table.setOnKeyPressed(e -> {
////			if (e.getCode() == KeyCode.DELETE) {
////				int n = table.getSelectionModel().getFocusedIndex();
////				controller.removeCategory(controller.getCategory(table.getItems().get(n).toString()));
////				update();
////			}
////		});
////		table.setOnMouseClicked(new EventHandler<MouseEvent>() {
////			@Override
////			public void handle(MouseEvent mouseEvent) {
////				if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
////					if(mouseEvent.getClickCount() == 2){
////						int n = table.getSelectionModel().getFocusedIndex();
////						Stage stage = new Stage();
////						EditNewCategoryDetailPane newCategoryDetailPane = new EditNewCategoryDetailPane(controller
////								, controller.getCategory(table.getItems().get(n).toString()));
////						newCategoryDetailPane.start(stage);
////						stage.show();
////					}
////				}
////			}
////		});
//	}
//
//	@Override
//	public void update() {
//
//	}
//
////	@Override
////	public void update() {
////		ObservableList<Category> data = FXCollections.observableArrayList(controller.getAllCategories());
////		table.setItems(data);
////	}
////
////	class NewListener implements EventHandler<ActionEvent> {
////        @Override
////        public void handle(ActionEvent e) {
////            Stage stage = new Stage();
////            NonEditNewCategoryDetailPane newCategoryDetailPane = new NonEditNewCategoryDetailPane(controller);
////            newCategoryDetailPane.start(stage);
////            stage.show();
////
////        }
////    }
	



