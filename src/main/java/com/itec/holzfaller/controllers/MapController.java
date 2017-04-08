package com.itec.holzfaller.controllers;

import com.itec.holzfaller.common.DateUtils;
import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.Journey;
import com.itec.holzfaller.entities.Location;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.services.UserService;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.*;

public class MapController implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

    private UserService userService = new UserService();

    @FXML
    protected GoogleMapView mapView;

    private GoogleMap map;

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

        Map<Marker, InfoWindowOptions> markerInfoWindowOptionsMap = getMarkers();
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
        mapView.addMapInializedListener(this);
    }

    public Map<Marker, InfoWindowOptions> getMarkers() {
        List<User> users = LoggedUserService.isAdmin() ? userService.findAll() : Arrays.asList(LoggedUserService.loggedUser);

        Map<Marker, InfoWindowOptions> markerWithOptions = new HashMap<>();

        for(User user : users) {
            List<Journey> journeys = user.getJourneys();
            if (journeys != null) {

                //compute markers
                for (Journey journey : journeys) {
                    Marker marker = createMarker(journey);
                    InfoWindowOptions options = createOptions(journey, user.getUsername());
                    markerWithOptions.put(marker, options);
                }
            }
        }
        System.out.println(markerWithOptions.size());
        return markerWithOptions;
    }

    private InfoWindowOptions createOptions(Journey journey, String username) {
        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content(username + "<br>"
                + "Cost " + journey.getCost() + "<br>"
                + "Start Date " + DateUtils.dateToString(journey.getStartDate()) + "<br>"
                + "End Date " + DateUtils.dateToString(journey.getEndDate()));
       return infoWindowOptions;
    }

    private Marker createMarker(Journey journey) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(journey.getLocation().computeLatLong());
        return new Marker(markerOptions);
    }

    public MarkerWrapper mapToMarkerWrapper(Journey journey) {
        Location location = journey.getLocation();

        return new MarkerWrapper(location.computeLatLong(), composeTitle(journey), location.getColor());
    }

    private String composeTitle(Journey journey) {
        Date startDate = journey.getStartDate();
        Date endDate = journey.getEndDate();

        System.out.println(DateUtils.dateToString(startDate) + "-" + DateUtils.dateToString(endDate));

        return DateUtils.dateToString(startDate) + "-" + DateUtils.dateToString(endDate);
    }

    private class MarkerWrapper {
        private LatLong latLong;
        private String title;
        private String color;

        public MarkerWrapper(LatLong latLong, String title, String color) {
            this.latLong = latLong;
            this.title = title;
            this.color = color;
        }

        public LatLong getLatLong() {
            return latLong;
        }

        public void setLatLong(LatLong latLong) {
            this.latLong = latLong;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
