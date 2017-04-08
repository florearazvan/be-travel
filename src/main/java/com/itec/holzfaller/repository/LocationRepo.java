package com.itec.holzfaller.repository;

import com.itec.holzfaller.entities.Location;

/**
 * Created by rbu on 4/8/17.
 */
public class LocationRepo extends Repository<Location, Long> {

    public LocationRepo(){
        super(Location.class);
    }

}
