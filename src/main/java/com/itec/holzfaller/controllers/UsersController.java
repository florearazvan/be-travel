package com.itec.holzfaller.controllers;

import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.StringJoiner;

public class UsersController {

    private UserService userService = new UserService();

    @FXML
    public TableView<User> usersTable;

    public void initialize() {
        initializeTableData();
        initializeTableEvents();
    }

    private void initializeTableEvents() {
        //double click event to edit user
        usersTable.setRowFactory(table -> {
            TableRow<User> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    User clickedUser = row.getItem();
                    LoggedUserService.username = clickedUser.getUsername();
                    ProfileController.showMe();
                }
            });

            return row;
        });
    }

    private void initializeTableData() {
        List<User> allUsers = userService.findAll();

        for (TableColumn column : usersTable.getColumns()) {
            column.setCellValueFactory(new PropertyValueFactory<User, StringJoiner>(column.getId()));
        }
        ObservableList<User> data = FXCollections.observableArrayList(allUsers);
        usersTable.setItems(data);
    }
}
