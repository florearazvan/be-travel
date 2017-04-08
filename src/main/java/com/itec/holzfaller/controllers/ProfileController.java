package com.itec.holzfaller.controllers;

import com.itec.holzfaller.common.DateUtils;
import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.Journey;
import com.itec.holzfaller.entities.Location;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.repository.LocationRepo;
import com.itec.holzfaller.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;


/**
 * Created by rbu on 4/7/17.
 */
public class ProfileController{

    private UserService userService = new UserService();
    private LocationRepo locationRepo = new LocationRepo();

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField email;

    @FXML
    private TableView<Journey> journeysTable;

    @FXML
    private Hyperlink addJourneyLink;

    @FXML
    private Button saveButton;

    private static Stage currentStage;

    private User currentUser;

    public void initialize(){
        if(LoggedUserService.username != "") {
            currentUser = userService.findByUsername(LoggedUserService.username);
        } else {
            currentUser = new User();
            currentUser.setJourneys(new ArrayList<>());
        }
        populateFields();
        populateTable();
        saveButton.setOnAction(event -> saveUser());
        addJourneyLink.setOnAction(event -> showAddJourneyPopUp());
        addChangeListeners();
    }

    private void populateTable() {
        for(TableColumn column : journeysTable.getColumns()){
            column.setCellValueFactory(new PropertyValueFactory<Journey, String>(column.getId()));
        }
        ObservableList<Journey> data =
                FXCollections.observableArrayList(currentUser.getJourneys());
        journeysTable.setItems(data);
        journeysTable.setRowFactory(table -> {
            TableRow<Journey> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    delete(row.getItem());
                }
            });
            return row;
        });
    }

    private void delete(Journey journey) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete journey");
        alert.setHeaderText("Are you sure you want to delete this journey? ");
        alert.setContentText("City:" + journey.getLocation().getName() +"\n" + "Start date:" + journey.getStartDate() + "\n" + "End date" + journey.getEndDate());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            currentUser.getJourneys().remove(journey);
            enableSaveButton();
            populateTable();
        }
    }

    private void addChangeListeners() {
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            enableSaveButton();
        });
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            enableSaveButton();
        });
        email.textProperty().addListener((observable, oldValue, newValue) -> {
            enableSaveButton();
        });
    }

    private void enableSaveButton() {
        saveButton.setDisable(false);
    }

    private void populateFields() {
        if (LoggedUserService.username != null) {
            if(LoggedUserService.username != "") {
                currentUser = userService.findByUsername(LoggedUserService.username);
            } else {
                currentUser = new User();
                currentUser.setJourneys(new ArrayList<>());
            }
            username.setText(currentUser.getUsername());
            password.setText(currentUser.getPassword());
            email.setText(currentUser.getEmail());
        }
    }

    private void saveUser() {
        currentUser.setUsername(username.getText());
        currentUser.setPassword(password.getText());
        currentUser.setEmail(email.getText());
        currentUser = userService.update(currentUser);
        currentStage.close();
    }

    private void showAddJourneyPopUp() {
        Journey newJourney = showPopupAndCreateJourney();
        currentUser.getJourneys().add(newJourney);
        populateTable();
        enableSaveButton();
    }

    private Journey showPopupAndCreateJourney() {
        Dialog<Journey> dialog = new Dialog<>();
        dialog.setTitle("Add new journey");
        dialog.setHeaderText("Add new journey");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        DatePicker startDate = new DatePicker();
        DatePicker endDate = new DatePicker();
        ObservableList<Location> locations = FXCollections.observableArrayList(locationRepo.findAll());
        ComboBox<Location> comboBox = new ComboBox(locations);
        TextField costTextField = new TextField();

        grid.add(new Label("Start date: "), 0, 0);
        grid.add(startDate, 1, 0);
        grid.add(new Label("End date: "), 0, 1);
        grid.add(endDate, 1, 1);
        grid.add(new Label("Location: "), 0, 2);
        grid.add(comboBox,1, 2);
        grid.add(new Label("Cost: "), 0, 3);
        grid.add(costTextField,1, 3);

        dialog.setResultConverter(addJurneyButton -> {
            Date start = DateUtils.getDateFromLocalDate(startDate.getValue());
            Date end = DateUtils.getDateFromLocalDate(endDate.getValue());
            Location location = comboBox.getValue();
            Double cost = Double.parseDouble(costTextField.getText());
            return  new Journey(start, end, cost, location);
        });

        dialog.getDialogPane().setContent(grid);
        Optional<Journey> journey = dialog.showAndWait();
        return journey.get();
    }

    public static void showMe(){
        Parent root;
        try {
            root = FXMLLoader.load(ProfileController.class.getClassLoader().getResource("ui/profile.fxml"));
            currentStage = new Stage();
            currentStage.setTitle(LoggedUserService.username.equals("") ? "Create new profile" : LoggedUserService.username + "'s profile");
            currentStage.setScene(new Scene(root, 450, 450));
            currentStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
