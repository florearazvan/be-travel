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

import javax.swing.event.HyperlinkListener;
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

    private static Stage currentStage;

    @FXML
    private Button saveButton;

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
        saveButton.setOnAction(event -> saveUser());
        addJourneyLink.setOnAction(event -> showAddJourneyPopUp());
    }


    private void populateFields() {
        if (LoggedUserService.username != null) {
            User user = userService.findByUsername(LoggedUserService.username);
            username.setText(user.getUsername());
            password.setText(user.getPassword());
            email.setText(user.getEmail());
        }
    }

    private void saveUser() {

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
