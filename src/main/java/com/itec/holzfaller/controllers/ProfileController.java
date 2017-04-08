package com.itec.holzfaller.controllers;

import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.Journey;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Created by rbu on 4/7/17.
 */
public class ProfileController{

    private UserService userService = new UserService();

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField email;

    @FXML
    private TableView<Journey> journeysTable;

    @FXML
    private Hyperlink addJourneyLink;

    @FXML
    private Button saveButton;

    private static Stage currentStage;

    private User currentUser;

    public void initialize(){
        populateFields();
        populateTable();
        addChangeListeners();
    }

    private void populateTable() {
        for(TableColumn column : journeysTable.getColumns()){
            column.setCellValueFactory(new PropertyValueFactory<Journey, String>(column.getId()));
        }
        ObservableList<Journey> data =
                FXCollections.observableArrayList(LoggedUserService.loggedUser.getJourneys());
        journeysTable.setItems(data);
        saveButton.setOnAction(event -> saveUser());
        addJourneyLink.setOnAction(event -> showAddJourneyPopUp());

    }

    private void addChangeListeners() {
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            enableSaveButton();
        });
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            enableSaveButton();
        });
        email.textProperty().addListener((observable, oldValue, newValue) -> {
            enableSaveButton();
        });
    }

    private void enableSaveButton() {
        saveButton.setDisable(false);
    }

    private void populateFields() {
        if (LoggedUserService.username != null) {
            currentUser = userService.findByUsername(LoggedUserService.username);
            username.setText(currentUser.getUsername());
            password.setText(currentUser.getPassword());
            email.setText(currentUser.getEmail());
        }
    }

    private void saveUser() {
        currentUser.setUsername(username.getText());
        currentUser.setPassword(password.getText());
        currentUser.setEmail(email.getText());
        currentUser = userService.update(currentUser);
    }

    private void showAddJourneyPopUp() {

    }

    public static void showMe(){
        Parent root;
        try {
            root = FXMLLoader.load(ProfileController.class.getClassLoader().getResource("ui/profile.fxml"));
            currentStage = new Stage();
            currentStage.setTitle(LoggedUserService.username + "'s profile");
            currentStage.setScene(new Scene(root, 450, 450));
            currentStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
