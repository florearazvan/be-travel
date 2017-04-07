package com.itec.holzfaller.controllers;

import com.itec.holzfaller.BeTravelApplication;
import com.itec.holzfaller.common.Constants;
import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class LoginController {

    private LoginService loginService = new LoginService();

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label errorLabel;

    @FXML
    public void login(ActionEvent actionEvent) {
        System.out.println("login....");

        String username = usernameTextField.getText() == null ? "" : usernameTextField.getText();
        String password = passwordTextField.getText() == null ? "" : passwordTextField.getText();

        User loginUser = loginService.loginUser(username, password);

        if (loginUser != null) {
            System.out.println("found user...");

            LoggedUserService.loggedUser = loginUser;
            goToOverview();
        } else {
            System.out.println("invalid credentials...");

            errorLabel.setTextFill(Color.web("#ff0000"));
            errorLabel.setVisible(true);
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
