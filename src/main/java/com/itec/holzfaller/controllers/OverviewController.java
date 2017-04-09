package com.itec.holzfaller.controllers;

import com.itec.holzfaller.BeTravelApplication;
import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.services.UserExportService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class OverviewController {

    UserExportService exportService = new UserExportService();

    @FXML
    private SplitPane splitPane;

    @FXML
    private Label viewUsers;

    @FXML
    private Label loggedinUser;

    @FXML
    private Label importData;

    @FXML
    private Label exportData;

    @FXML
    private Label logout;

    public void initialize() {
        loggedinUser.setText(LoggedUserService.loggedUser.getUsername());
        if (LoggedUserService.isConsultant()) {
            viewUsers.setVisible(false);
            importData.setVisible(false);
            exportData.setVisible(false);
        }
        loggedinUser.setOnMouseClicked((MouseEvent event) ->{
            if (event.getClickCount() == 2) {
                switchProfileView();
            }
        });
        logout.setOnMouseClicked((MouseEvent) -> logout());
    }

    private void logout() {
        try {
            deleteUserCredentials();
            Pane login = FXMLLoader.load(getClass().getClassLoader().getResource("ui/login.fxml"));
            BeTravelApplication.changeScene("Login", new Scene(login));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteUserCredentials() {
        LoggedUserService.username = "";
        LoggedUserService.loggedUser = null;
    }

    public void switchToUsersView() {
        System.out.println("switching to users...");
        changeTo("ui/users.fxml");
    }

    public void switchMapView() {
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

    public void switchReportView() {
        System.out.println("switching to reports...");
        changeTo("ui/report.fxml");
    }

    public void switchImportView() {
        System.out.println("switching to import...");
        changeTo("ui/import.fxml");
    }

    public void export() {
        String messageInDialog;
        try {
            exportService.export(null);
            messageInDialog = "Data exported successfully!";
        } catch (IOException e) {
            messageInDialog = "Something went wrong!";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(messageInDialog);

        alert.showAndWait();
    }
}
