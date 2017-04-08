package com.itec.holzfaller.controllers;

import com.itec.holzfaller.services.ImportService;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by rbu on 4/8/17.
 */
public class ImportController {

    private ImportService importService = new ImportService();

    public void initialize(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        String messageInDialog;
        if(importService.importJson(file)){
            messageInDialog = "Data imported successfully!";
        } else {
            messageInDialog = "Something went wrong";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(messageInDialog);

        alert.showAndWait();
    }

}
