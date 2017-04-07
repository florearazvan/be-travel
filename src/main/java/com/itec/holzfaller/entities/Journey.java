package com.itec.holzfaller.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Journey {

    @Id
    @GeneratedValue
    private long id;

    private LocalDateTime startDate;

    private LocalDateTime endTime;

    private double cost;

    @OneToOne
    private Location locastion;

    //TODO start date, end date, user, location, cost

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Location getLocastion() {
        return locastion;
    }

    public void setLocastion(Location locastion) {
        this.locastion = locastion;
    }
}
