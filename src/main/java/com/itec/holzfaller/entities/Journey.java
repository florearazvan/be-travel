package com.itec.holzfaller.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Journey {

    @Id
    @GeneratedValue
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    private double cost;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Location location;

    public Journey() {
    }

    public Journey(Date startDate, Date endDate, double cost, Location location) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Journey{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", cost=" + cost +
                ", location=" + location +
                '}';
    }
}
