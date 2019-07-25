package view.panels;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Observer;

import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class InstellingenPane extends GridPane implements Observer {
    private Controller controller;
    private Button btnOK, btnCancel;
    private TextField titleField, descriptionField;
    private ComboBox dataTypeField;



    public InstellingenPane(Controller controller) {

        this.controller = controller;
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("tekst");
        arrayList.add("excel");
        this.setPrefHeight(150);
        this.setPrefWidth(300);

        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        this.add(new Label("Which dataType:"), 0, 2, 1, 1);
        dataTypeField = new ComboBox<>();
        dataTypeField.setItems(FXCollections.observableArrayList(arrayList));
        this.add(dataTypeField, 1, 2, 1, 1);

        btnCancel = new Button("Cancel");
        this.add(btnCancel, 0, 3, 1, 1);
        setCancelAction(new CancelListener());

        btnOK = new Button("Select");
        btnOK.isDefaultButton();
        this.add(btnOK, 1, 3, 1, 1);
        setSaveAction(new SelectListener());

    }

    @Override
    public void update() {

    }

    class SelectListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            System.out.println("Started");
            if (dataTypeField.getSelectionModel().isEmpty())
                new CancelListener();
            else{
                try {
                    System.out.println("try to load");
                Properties properties = new Properties();
                InputStream is = null;
                is = new FileInputStream("evaluation.properties");

                properties.load(is);
                    System.out.println("loaded");
                    properties.setProperty("type",dataTypeField.getSelectionModel().getSelectedItem().toString());
                    String out = "type="+properties.getProperty("type");
               properties.store(new FileOutputStream("evaluation.properties"),out);
                    System.out.println("set to "+properties.getProperty("type"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
//                category = controller.getCategory(dataTypeField.getSelectionModel().getSelectedItem().toString());
//            Stage staage = new Stage();
//            EvaluationTest evaluationTest = new EvaluationTest(category);
//            new StartQuestionaire(staage, evaluationTest);
//            final Node source = (Node) e.getSource();
//            final Stage stage = (Stage) source.getScene().getWindow();
//            stage.close();
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
