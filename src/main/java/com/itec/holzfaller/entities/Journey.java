package com.itec.holzfaller.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Journey {

    @Id
    @GeneratedValue
    private long id;

    //TODO start date, end date, user, location, cost

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
