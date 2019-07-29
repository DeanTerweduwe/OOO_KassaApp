package view.panels;

import controller.Controller;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class KlantView {
	private Stage stage = new Stage();
	private Controller controller;
	private Pane klantArtikelOverviewPane = new KlantArtikelOverviewPane(controller);


	public KlantView(){


		stage.setTitle("KLANT VIEW");
		stage.setResizable(false);		
		stage.setX(775);
		stage.setY(20);
		Group root = new Group();
		Scene scene = new Scene(root, 500, 500);

		klantArtikelOverviewPane.prefHeightProperty().bind(scene.heightProperty());
		klantArtikelOverviewPane.prefWidthProperty().bind(scene.widthProperty());
		root.getChildren().add(klantArtikelOverviewPane);




		stage.setScene(scene);
		stage.sizeToScene();			
		stage.show();		
	}
}
