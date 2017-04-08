package com.itec.holzfaller.repository;

import com.itec.holzfaller.entities.Journey;

public class JourneyRepo extends Repository<Journey, Long>{

    public JourneyRepo() {
        super(Journey.class);
    }
}
