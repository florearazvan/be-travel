package com.itec.holzfaller.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;

import java.io.IOException;
import java.net.URL;

public class OverviewController {

    @FXML
    private SplitPane splitPane;

    public void initialize() {
        System.out.println("initializing...");
        for(int i=0; i<splitPane.getItems().size(); i++) {
            System.out.println(i);
            System.out.println(splitPane.getItems().get(i));
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
