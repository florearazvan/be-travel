package com.itec.holzfaller.controllers;

import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.services.ReportService;
import com.itec.holzfaller.services.SendEmailService;
import com.itec.holzfaller.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.mail.MessagingException;
import java.util.stream.Collectors;

/**
 * Created by rbu on 4/8/17.
 */
public class ReportController {

    private ReportService reportService = new ReportService();
    private UserService userService = new UserService();
    private SendEmailService sendEmailService = new SendEmailService();

    @FXML
    private Label reportLocation;

    @FXML
    private Label reportForUser;

    @FXML
    private ComboBox<String> userList;

    @FXML
    private Button createReport;

    @FXML
    private Hyperlink sendEmail;

    public void initialize(){
        initComboBox();
        initButton();
    }

    private void initComboBox() {
        ObservableList<String> users = FXCollections.observableArrayList(userService.findAll().stream().map(User::getUsername).collect(Collectors.toList()));
        userList.setItems(users);
        if(LoggedUserService.isConsultant()){
            userList.setDisable(true);
            userList.setValue(LoggedUserService.loggedUser.getUsername());
        }
    }

    private void initButton(){
        createReport.setOnAction(event -> {
            String usernameForReport = userList.getValue().isEmpty() ? LoggedUserService.loggedUser.getUsername() : userList.getValue();
            reportService.createPDFReportFor(usernameForReport);
            reportLocation.setText("PDF Report is int: " + System.getProperty("user.dir"));
            reportForUser.setText(reportService.getStringReport(usernameForReport));
            sendEmail.setDisable(false);
            sendEmail.setText("Send the report through e-mail to " + usernameForReport);
            sendEmail.setOnAction( event1 ->{
                String dialogContent = sendEmail(usernameForReport);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText(dialogContent);

                alert.showAndWait();
            });
        });
    }

    private String sendEmail(String usernameForReport) {
        try {
            return sendEmailService.sendStringReportTo(usernameForReport);
        } catch (MessagingException e) {
            return "Email was not sent!";
        }
    }

}
