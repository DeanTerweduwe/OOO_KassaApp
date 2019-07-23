package application;

import javafx.application.Application;
import javafx.stage.Stage;
import model.db.ArtikelDBTekst;
import model.db.DBService;
import view.panels.KassaView;
import view.panels.KlantView;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		KassaView kassaView = new KassaView();
		KlantView klantView = new KlantView();


	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
