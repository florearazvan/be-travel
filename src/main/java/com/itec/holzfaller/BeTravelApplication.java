package com.itec.holzfaller;

import com.itec.holzfaller.common.Constants;
import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.Journey;
import com.itec.holzfaller.entities.Location;
import com.itec.holzfaller.entities.Role;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.repository.LocationRepo;
import com.itec.holzfaller.repository.UserRepo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class BeTravelApplication extends Application {

    private static Stage primaryStage;

    public static void main(String[] args) {
        //TODO maybe here open another thread for the CLI

        initDB();
        launch(args);
    }

    private static void initDB() {
        LocationRepo locationRepo = new LocationRepo();
        Location muc;
        Location tm;
        Location hun;
        if (locationRepo.findAll().size() == 0){
            muc = new Location("München", 48.15, 11.26, "#2c5d9d");
            tm = new Location("Timișoara", 45.74, 21.14, "#bcdf46");
            Location frk = new Location("Frankfurt am Main", 50.12, 8.49, "#a31919");
            Location wien = new Location("Wien", 48.22, 16.09, "#0c6e48");
            Location ams = new Location("Amsterdam", 52.34, 4.75, "#ff8000");
            hun = new Location("Hunedoara", 45.76, 22.87, "#ff8000");

            muc = locationRepo.update(muc);
            tm = locationRepo.update(tm);
            locationRepo.save(frk);
            locationRepo.save(wien);
            locationRepo.save(ams);
            hun = locationRepo.update(hun);
    } else
    {
        muc = locationRepo.findAll().stream().filter(location -> location.getName().equals("München")).findFirst().get();
        tm = locationRepo.findAll().stream().filter(location -> location.getName().equals("Timișoara")).findFirst().get();
        hun = locationRepo.findAll().stream().filter(location -> location.getName().equals("Hunedoara")).findFirst().get();
    }

        UserRepo userRepo = new UserRepo();
        if (userRepo.findByUsername("admin") == null) {
            User defaultUser = new User("admin", "admin", "admin@itec.com", Role.ADMIN);
            defaultUser.setJourneys(
                    Arrays.asList(new Journey(nextDay(), nextWeek(), 10.0, tm),
                            new Journey(new Date(), new Date(), 10.0, hun)));
            defaultUser.setLocation(tm);
            userRepo.update(defaultUser);
        }

        if (userRepo.findByUsername("user") == null) {
            User consultant = new User("user", "user", "user@itec.com", Role.CONSULTANT);
            consultant.setJourneys(Arrays.asList(new Journey(new Date(), new Date(), 10.0, muc)));
            consultant.setLocation(hun);
            userRepo.update(consultant);
        }
    }

    private static Date nextDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    private static Date nextWeek() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
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
