package com.itec.holzfaller.controllers;

import com.itec.holzfaller.common.DateUtils;
import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.Journey;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.services.UserService;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.itec.holzfaller.common.DateUtils.*;

public class MapController implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

    @FXML
    private HBox filterContainer;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField usernameTextField;

    @FXML
    private DatePicker startDatePicker;

    private UserService userService = new UserService();

    @FXML
    protected GoogleMapView mapView;

    private GoogleMap map;

    private List<User> users;

    @Override
    public void mapInitialized() {

        MapOptions options = new MapOptions();

        options.center(new LatLong(45.7494, 21.2272))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(6);

        map = mapView.createMap(options);

        users = LoggedUserService.isAdmin() ? userService.findAll() : Arrays.asList(LoggedUserService.loggedUser);
        addMarkersToMap(mapUsernameToJourneys(users));
    }

    private Map<String, List<Journey>> mapUsernameToJourneys(List<User> users) {
        Map<String, List<Journey>> userWithJourneys = new HashMap<>();

        for(User user : users) {
            userWithJourneys.put(user.getUsername(), user.getJourneys());
        }

        return userWithJourneys;
    }

    private void addMarkersToMap(Map<String, List<Journey>> usersWithJourneys) {
        Map<Marker, InfoWindowOptions> markerInfoWindowOptionsMap = getMarkers(usersWithJourneys);

        map.clearMarkers();

        for (Marker marker : markerInfoWindowOptionsMap.keySet()) {
            map.addMarker(marker);
            InfoWindowOptions infoWindowOptions = markerInfoWindowOptionsMap.get(marker);
            InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
            infoWindow.open(map, marker);
        }
    }

    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (LoggedUserService.isConsultant()) {
            filterContainer.getChildren().remove(usernameTextField);
        }
        mapView.addMapInializedListener(this);
    }

    @FXML
    public void filterMarkers(ActionEvent actionEvent) {
        System.out.println("filtering...");

        Map<String, List<Journey>> filteredJourneys = filterUsers(startDatePicker.getValue(), endDatePicker.getValue(), usernameTextField.getText());
        addMarkersToMap(filteredJourneys);

    }

    private Map<String, List<Journey>> filterUsers(LocalDate startDate, LocalDate endDate, String username) {
        Map<String, List<Journey>> usersWithJourneys = new HashMap<>();

        List<User> filteredUsers = filterUsers(username);

        for (User filteredUser : filteredUsers) {
            usersWithJourneys.put(filteredUser.getUsername(), filterJourneys(filteredUser.getJourneys(), startDate, endDate));
        }

        return usersWithJourneys;
    }

    private List<Journey> filterJourneys(List<Journey> journeys, LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return journeys.stream()
                           .filter(journey -> before(getDateFromLocalDate(startDate), journey.getStartDate()) &&
                                   before(journey.getEndDate(), getDateFromLocalDate(endDate)))
                           .collect(Collectors.toList());
        } else {
            return journeys;
        }

    }

    private List<User> filterUsers(String username) {
        if (username != null && !"".equals(username)) {
            return users.stream()
                        .filter(user -> username.equals(user.getUsername()))
                        .collect(Collectors.toList());
        }
        return users;
    }

    public Map<Marker, InfoWindowOptions> getMarkers(Map<String, List<Journey>> usersWithJourneys) {
        Map<Marker, InfoWindowOptions> markerWithOptions = new HashMap<>();

        for(String username : usersWithJourneys.keySet()) {
            List<Journey> journeys = usersWithJourneys.get(username);
            if (journeys != null) {

                //compute markers
                for (Journey journey : journeys) {
                    Marker marker = createMarker(journey);
                    InfoWindowOptions options = createOptions(journey, username);
                    markerWithOptions.put(marker, options);
                }
            }
        }
        return markerWithOptions;
    }

    private InfoWindowOptions createOptions(Journey journey, String username) {
        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content(username + "<br>"
                + "Cost " + journey.getCost() + "<br>"
                + "Start Date " + dateToString(journey.getStartDate()) + "<br>"
                + "End Date " + dateToString(journey.getEndDate()));
       return infoWindowOptions;
    }

    private Marker createMarker(Journey journey) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(journey.getLocation().computeLatLong());
        return new Marker(markerOptions);
    }

}
