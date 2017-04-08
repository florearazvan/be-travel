package com.itec.holzfaller.controllers;

import com.itec.holzfaller.services.ImportService;
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
        System.out.println(importService.importJson(file));
    }

}
