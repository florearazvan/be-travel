package com.itec.holzfaller.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Location {

    @Id
    @GeneratedValue
    private long id;

    //TODO add latitude/longitude & maybe user relation???

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}