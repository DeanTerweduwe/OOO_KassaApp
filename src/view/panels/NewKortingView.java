package view.panels;

import controller.Controller;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.KortingsType;


public class NewKortingView {
        private Stage stage = new Stage();
        private Controller controller;
        private Pane newKortingPane;


        public NewKortingView(KortingsType kortingsType){
            newKortingPane= new NewKortingPane(controller,kortingsType);

            stage.setTitle("Add " + kortingsType.toString()+":");
            stage.setResizable(false);
            stage.setX(775);
            stage.setY(20);
            Group root = new Group();
            Scene scene = new Scene(root, 500, 500);

            newKortingPane.prefHeightProperty().bind(scene.heightProperty());
            newKortingPane.prefWidthProperty().bind(scene.widthProperty());
            root.getChildren().add(newKortingPane);




            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        }
    }



