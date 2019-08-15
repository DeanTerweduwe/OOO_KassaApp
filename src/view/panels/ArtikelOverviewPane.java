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


	@Override
	public void update() {
		ObservableList<Artikel> data = FXCollections.observableArrayList(controller.getAllArtikels().values());
		table.setItems(data);
	}
//


}





