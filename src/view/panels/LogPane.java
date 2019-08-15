package view.panels;

import controller.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Artikel;
import model.KortingsType;
import model.Log;
import model.Observer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class LogPane extends GridPane implements Observer   {


        private Controller controller;
        private TableView table;
        private Label log;
        private SimpleStringProperty simpleStringProperty=new SimpleStringProperty();




    public LogPane(Controller controller) {



            this.controller = controller;
            this.setPrefHeight(150);
            this.setPrefWidth(300);
            controller.registerDBObserver(this::update);


            this.setPadding(new Insets(5, 5, 5, 5));
            this.setVgap(5);
            this.setHgap(5);



        table = new TableView<>();
        table.setPrefWidth(REMAINING);
        TableColumn timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("nowString"));
        timeCol.setPrefWidth(200);
        table.getColumns().add(timeCol);

        TableColumn totCol = new TableColumn<>("Totaal");
        totCol.setCellValueFactory(new PropertyValueFactory("totaal"));
        table.getColumns().add(totCol);

        TableColumn kortCol = new TableColumn<>("Korting");
        kortCol.setCellValueFactory(new PropertyValueFactory("korting"));
        table.getColumns().add(kortCol);

        TableColumn betCol = new TableColumn<>("Te Betalen");
        betCol.setCellValueFactory(new PropertyValueFactory("teBetalen"));
        table.getColumns().add(betCol);



        this.add(table, 0, 1, 5, 10);
        ObservableList<Log> data = FXCollections.observableArrayList(controller.getLog());

        table.setItems(data);









        }

        @Override
        public void update() {
            ObservableList<Log> data = FXCollections.observableArrayList(controller.getLog());

            table.setItems(data);

        }



    }




