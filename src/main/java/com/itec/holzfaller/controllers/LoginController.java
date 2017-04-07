package com.itec.holzfaller.controllers;

import com.itec.holzfaller.BeTravelApplication;
import com.itec.holzfaller.common.Constants;
import com.itec.holzfaller.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LoginController {

    private LoginService loginService = new LoginService();

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    public void login(ActionEvent actionEvent) {
        System.out.println("login....");

        String username = usernameTextField.getText() == null ? "" : usernameTextField.getText();
        String password = passwordTextField.getText() == null ? "" : passwordTextField.getText();

        if (loginService.loginUser(username, password)) {
            System.out.println("found user...");

            goToOverview();
        } else {
            //TODO handle invalid credentials
            System.out.println("invalid credentials...");
        }
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
