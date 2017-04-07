package com.itec.holzfaller.controllers;

import com.itec.holzfaller.BeTravelApplication;
import com.itec.holzfaller.common.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    public void login(ActionEvent actionEvent) {
        System.out.println("login....");

        //TODO add login check

        goToOverview();
    }

    private void goToOverview() {
        try {
            Pane overviewPane = FXMLLoader.load(getClass().getClassLoader().getResource("ui/overview.fxml"));
            BeTravelApplication.changeScene(Constants.APP_NAME, new Scene(overviewPane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
