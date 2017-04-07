package com.itec.holzfaller;

import com.itec.holzfaller.common.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class BeTravelApplication extends Application {

    private static Stage primaryStage;

    public static void main(String[] args) {
        //TODO maybe here open another thread for the CLI
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        primaryStage.setTitle(Constants.APP_NAME);

        URL url = getClass().getClassLoader().getResource("ui/login.fxml");
        Parent root = FXMLLoader.load(url);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void changeScene(String title, Scene scene){
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
