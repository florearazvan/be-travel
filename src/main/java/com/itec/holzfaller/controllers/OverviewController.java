package com.itec.holzfaller.controllers;

import com.itec.holzfaller.common.LoggedUserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;

public class OverviewController {

    @FXML
    private SplitPane splitPane;

    @FXML
    private Button viewUsers;

    @FXML
    private Label loggedinUser;

    public void initialize() {
        loggedinUser.setText(LoggedUserService.loggedUser.getUsername());
        if (LoggedUserService.isConsultant()) {
            viewUsers.setVisible(false);
        }
        loggedinUser.setOnMouseClicked((MouseEvent event) ->{
            if (event.getClickCount() == 2) {
                switchProfileView();
            }
        });
    }

    public void switchToUsersView(ActionEvent actionEvent) {
        System.out.println("switching to users...");
        changeTo("ui/users.fxml");
    }

    public void switchProfileView(ActionEvent actionEvent) {
        System.out.println("switching to map...");
        changeTo("ui/map.fxml");
    }

    protected void changeTo(String pageUrl) {
        try {
            URL url = getClass().getClassLoader().getResource(pageUrl);
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            splitPane.getItems().set(1, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchProfileView() {
        LoggedUserService.username = loggedinUser.getText();
        ProfileController.showMe();
    }

    public void switchReportView(ActionEvent actionEvent) {
        System.out.println("switching to reports...");
        changeTo("ui/report.fxml");
    }

    public void switchImportView(ActionEvent actionEvent) {
        System.out.println("switching to import...");
        changeTo("ui/import.fxml");
    }
}
