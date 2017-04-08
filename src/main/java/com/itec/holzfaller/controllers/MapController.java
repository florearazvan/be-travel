package com.itec.holzfaller.controllers;

import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.Journey;
import com.itec.holzfaller.entities.Location;
import com.itec.holzfaller.entities.User;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MapController implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

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
                .zoom(12);

        map = mapView.createMap(options);

        List<Marker> markers = getMarkers();
        markers.forEach(marker -> map.addMarker(marker));
    }

    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
    }

    public List<Marker> getMarkers() {
        User loggedUser = LoggedUserService.loggedUser;
        List<Journey> journeys = loggedUser.getJourneys();

        if (journeys != null) {

            //compute markers
            List<Marker> markers = journeys.stream()
                    .map(Journey::getLocation)
                    .map(Location::computeLatLong)
                    .map(latLong -> {
                        MarkerOptions option = new MarkerOptions();
                        option.position(latLong);
                        return option;
                    })
                    .map(Marker::new)
                    .collect(Collectors.toList());

            return markers;
        }

        return new ArrayList<>();
    }
}
