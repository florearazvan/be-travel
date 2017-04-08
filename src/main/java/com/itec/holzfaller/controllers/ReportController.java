package com.itec.holzfaller.controllers;

import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.services.ReportService;
import com.itec.holzfaller.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.stream.Collectors;

/**
 * Created by rbu on 4/8/17.
 */
public class ReportController {

    private ReportService reportService = new ReportService();
    private UserService userService = new UserService();

    @FXML
    private Label reportLocation;

    @FXML
    private Label reportForUser;

    @FXML
    private ComboBox<String> userList;

    @FXML
    private Button createReport;

    public void initialize(){
        initComboBox();
        initButton();
    }

    private void initComboBox() {
        ObservableList<String> users = FXCollections.observableArrayList(userService.findAll().stream().map(User::getUsername).collect(Collectors.toList()));
        userList.setItems(users);
    }

    private void initButton(){
        createReport.setOnAction(event -> {
            reportService.createPDFReportFor(userList.getValue().isEmpty() ? LoggedUserService.loggedUser.getUsername() : userList.getValue());
            reportLocation.setText("PDF Report is int: " + System.getProperty("user.dir"));
            reportForUser.setText(reportService.getStringReport(userList.getValue().isEmpty() ? LoggedUserService.loggedUser.getUsername() : userList.getValue()));
        });
    }

}
