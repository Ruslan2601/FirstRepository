package com.schedule.rest.repositories;

import com.schedule.rest.models.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Integer> {
    Optional<Station> findByName(String name);
}
