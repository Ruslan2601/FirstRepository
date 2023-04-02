package com.schedule.rest.services.impl;

import com.schedule.rest.models.Station;
import com.schedule.rest.repositories.StationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StationServiceImplTest {

    @InjectMocks
    StationServiceImpl stationService;

    @Mock
    StationRepository stationRepository;

    @Test
    public void save() {
        Station station = new Station();
        station.setName("Mordor");

        Mockito.when(stationRepository.save(station)).thenReturn(station);

        boolean isStationSaved = stationService.save(station);

        Assertions.assertTrue(isStationSaved);
    }

    @Test
    public void shouldReturnFindByName() {
        String name = "Mordor";

        Mockito.when(stationRepository.findByName(name)).thenReturn(Optional.of(new Station("Mordor")));
        Station station = stationService.findByName(name).orElse(null);

        Assertions.assertNotNull(station);
        Assertions.assertEquals(station.getName(), "Mordor");
    }
}
