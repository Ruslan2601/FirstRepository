package com.schedule.rest.services.impl;

import com.schedule.rest.models.Station;
import com.schedule.rest.repositories.StationRepository;
import com.schedule.rest.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    @Autowired
    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    @Transactional
    public boolean save(Station station) {
        stationRepository.save(station);
        return true;
    }

    @Override
    public Optional<Station> findByName(String name) {
       return stationRepository.findByName(name);
    }

}
