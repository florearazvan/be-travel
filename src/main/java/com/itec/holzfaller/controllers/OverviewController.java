package com.itec.holzfaller.controllers;

import com.itec.holzfaller.common.LoggedUserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;

import java.io.IOException;
import java.net.URL;

public class OverviewController {

    @FXML
    private SplitPane splitPane;

    @FXML
    private Button viewUsers;

    public void initialize() {
        System.out.println("initializing...");

        if (LoggedUserService.isConsultant()) {
            viewUsers.setVisible(false);
        }
    }

    public void switchToUsersView(ActionEvent actionEvent) {
        System.out.println("switching to users...");
        changeTo("ui/users.fxml");
    }

    public void switchToMapView(ActionEvent actionEvent) {
        System.out.println("switching to map...");
        changeTo("ui/map.fxml");
    }

    public boolean isAdmin() {
        return false;
    }

    private void changeTo(String pageUrl) {
        try {
            URL url = getClass().getClassLoader().getResource(pageUrl);
            Parent root = FXMLLoader.load(url);
            splitPane.getItems().set(1, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
