package com.schedule.rest.services;

import com.schedule.rest.dto.*;
import com.schedule.rest.models.Train;

import java.util.List;
import java.util.Optional;


public interface TrainService {

    Train find(int id);

    boolean save(Train train);

    Optional<Train> findByNumber(int number);

    boolean findEmptyPlaces(CustomerTicketDTO customerTicketDTO);

    List<TrainDTO> findAllTrains();

    List<PassengerDTO> findAllPassengers(int id);

    List<ScheduleForTrainDTO> findScheduleForTrains();

    List<ScheduleForTrainDTO> searchTrains(CustomerRouteDTO customerRouteDTO);

}
