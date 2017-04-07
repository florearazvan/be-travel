package com.itec.holzfaller.controllers;

import com.itec.holzfaller.BeTravelApplication;
import com.itec.holzfaller.common.Constants;
import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.Journey;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.stream.Collectors;


/**
 * Created by rbu on 4/7/17.
 */
public class ProfileController{

    @FXML
    private Label username;

    @FXML
    private Label password;

    @FXML
    private Label email;

    @FXML
    private TableView<Journey> journeysTable;

    public void initialize(){
        populateFields();
        populateTable();
    }

    private void populateTable() {
        for(TableColumn column : journeysTable.getColumns()){
            column.setCellValueFactory(new PropertyValueFactory<Journey, String>(column.getId()));
        }
        ObservableList<Journey> data =
                FXCollections.observableArrayList(LoggedUserService.loggedUser.getJourneys());
        journeysTable.setItems(data);
    }


    private void populateFields() {
        username.setText(LoggedUserService.loggedUser.getUsername());
        password.setText(LoggedUserService.loggedUser.getPassword());
        email.setText(LoggedUserService.loggedUser.getEmail());
    }

}
