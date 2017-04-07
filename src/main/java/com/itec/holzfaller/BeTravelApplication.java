package com.itec.holzfaller;

import com.itec.holzfaller.common.Constants;
import com.itec.holzfaller.entities.Journey;
import com.itec.holzfaller.entities.Location;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.repository.UserRepo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.Date;

public class BeTravelApplication extends Application {

    private static Stage primaryStage;

    public static void main(String[] args) {
        //TODO maybe here open another thread for the CLI

        initDefaultUser();
        launch(args);
    }

    private static void initDefaultUser() {
        UserRepo userRepo = new UserRepo();
        if (userRepo.findOne(1L) == null) {
            User defaultUser = new User("admin", "admin", "admin@itec.com");
            defaultUser.setJourneys(Arrays.asList(new Journey(new Date(), new Date(), 10.0, new Location("Timisoara"))));
            userRepo.save(defaultUser);
        }
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
