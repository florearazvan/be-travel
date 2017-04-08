package com.itec.holzfaller.controllers;

import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.Journey;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * Created by rbu on 4/7/17.
 */
public class ProfileController{

    private UserService userService = new UserService();

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
        if (LoggedUserService.username != null) {
            User user = userService.findByUsername(LoggedUserService.username);
            username.setText(user.getUsername());
            password.setText(user.getPassword());
            email.setText(user.getEmail());
        }
    }

}