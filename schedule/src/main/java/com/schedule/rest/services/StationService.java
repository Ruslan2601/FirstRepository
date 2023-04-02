package com.schedule.rest.services;

import com.schedule.rest.models.Station;

import java.util.Optional;

public interface StationService {

    Optional<Station> findByName(String name);

    boolean save(Station station);
}
