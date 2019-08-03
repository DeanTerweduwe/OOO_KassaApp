package view.panels;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.*;

public class NewKortingPane extends GridPane {
    private Button btnOK, btnCancel;
    private TextField persentageField,drempelField;
    private ComboBox groepField;
    private Controller controller;
    private KortingsType kortingsType;



    public NewKortingPane(Controller controller, KortingsType kortingsType) {



        this.kortingsType = kortingsType;
        this.controller = controller;
        this.setPrefHeight(150);
        this.setPrefWidth(300);

        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        this.add(new Label("Persentage:"), 0, 0, 1, 1);
        persentageField = new TextField();
        this.add(persentageField, 1, 0, 1, 1);

        System.out.println(kortingsType);
        if(kortingsType == KortingsType.groepkorting){
            this.add(new Label("Groep:"),0,1,1,1);
            groepField = new ComboBox<>();
            groepField.setItems(FXCollections.observableArrayList(controller.getAllArtikelGroepen()));
            this.add(groepField, 1, 1, 1, 1);

        }

        if(kortingsType == KortingsType.drempelkorting){
            this.add(new Label("Drempel Prijs:"),0,1,1,1);
            drempelField = new TextField();
            this.add(drempelField, 1, 1, 1, 1);

        }





        btnCancel = new Button("Cancel");
        this.add(btnCancel, 0, 3, 1, 1);
        setCancelAction(new CancelListener());

        btnOK = new Button("Save");
        btnOK.isDefaultButton();
        this.add(btnOK, 1, 3, 1, 1);
        setSaveAction(new SaveListener());

    }


    class SaveListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            try {


                if (kortingsType == KortingsType.groepkorting) {
                    System.out.println("Groepskoring met persentage: " + persentageField.getText() + " en Groep: " + groepField.getSelectionModel().getSelectedItem().toString());
                    Korting korting = new GroepKorting(Double.parseDouble(persentageField.getText()), ArtikelGroep.valueOf(groepField.getSelectionModel().getSelectedItem().toString()));
                    System.out.println(korting.getPersentage());
                    System.out.println(((GroepKorting) korting).getArtikelGroep().toString());

                    controller.addKorting(korting);
                    System.out.println("added");
                }


                if (kortingsType == KortingsType.drempelkorting) {
                    System.out.println("Drempelkorting met persentage: " + persentageField.getText() + " en Drempel prijs: " + drempelField.getText());
                    Korting korting = new DrempelKorting(Double.parseDouble(persentageField.getText()), Double.parseDouble(drempelField.getText()));
                    controller.addKorting(korting);
                    System.out.println("added");

                }

                if (kortingsType == KortingsType.duurstekorting) {
                    System.out.println("Duurstekorting met persentage: " + persentageField.getText());
                    Korting korting = new DuursteKorting(Double.parseDouble(persentageField.getText()));
                    controller.addKorting(korting);
                    System.out.println("added");


                }

                final Node source = (Node) e.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();


            }
            catch (Exception exeption){
                Alert alert = new Alert(Alert.AlertType.ERROR,"Niet Alle velden zijn juist ingevuld \n\r" +exeption.getMessage(),ButtonType.CLOSE);
                alert.showAndWait();
            }



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

    public void setSaveAction(EventHandler<ActionEvent> saveAction) {
        btnOK.setOnAction(saveAction);
    }

    public void setCancelAction(EventHandler<ActionEvent> cancelAction) {
        btnCancel.setOnAction(cancelAction);
    }


}
