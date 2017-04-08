package com.itec.holzfaller.controllers;

import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class UsersController {

    private UserService userService = new UserService();

    @FXML
    public TableView<User> usersTable;

    @FXML
    private Hyperlink addUser;

    public void initialize() {
        initializeTableData();
        initializeTableEvents();
        addUser.setOnAction(event -> createUser());
    }

    private void createUser() {
        LoggedUserService.username="";
        ProfileController.showMe();
    }

    private void initializeTableData() {
        List<User> allUsers = userService.findAll();

        for (TableColumn column : usersTable.getColumns()) {
            column.setCellValueFactory(new PropertyValueFactory<User, StringJoiner>(column.getId()));
        }
        ObservableList<User> data = FXCollections.observableArrayList(allUsers);
        usersTable.setItems(data);
    }

    private void initializeTableEvents() {
        //double click event to edit user
        usersTable.setRowFactory(table -> {
            TableRow<User> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
//                    User clickedUser = row.getItem();
//                    LoggedUserService.username = clickedUser.getUsername();
//                    ProfileController.showMe();
                    showActionPopUp(row.getItem());
                }
            });

            return row;
        });
    }

    private void showActionPopUp(User user) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog with Custom Actions");
        alert.setHeaderText("Look, a Confirmation Dialog with Custom Actions");
        alert.setContentText("Choose your option.");

        ButtonType buttonEdit = new ButtonType("View/Edit");
        ButtonType buttonDelete = new ButtonType("Delete");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonEdit, buttonDelete, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonDelete){
            delete(user);
        } else if (result.get() == buttonEdit) {
            edit(user);
        }
    }

    private void delete(User user) {
        System.out.println("Delete user...");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete " + user.getUsername());
        alert.setHeaderText("Are you sure you want to delete user?");
        alert.setContentText(user.toString());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            userService.delete(user);
            initializeTableData();
        }
    }

    private void edit(User user) {
        System.out.println("Edit user...");
        LoggedUserService.username = user.getUsername();
        ProfileController.showMe();
    }

}
