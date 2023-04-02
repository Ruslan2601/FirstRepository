package com.schedule.rest.services.impl;

import com.schedule.rest.dto.*;
import com.schedule.rest.models.Train;
import com.schedule.rest.repositories.TrainRepository;
import com.schedule.rest.services.TrainService;
import com.schedule.rest.util.TrainNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;

    @Autowired
    public TrainServiceImpl(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @Override
    public Train find(int id) {
        Optional<Train> foundTrain = trainRepository.findById(id);
        return foundTrain.orElseThrow(TrainNotFoundException::new);
    }

    @Override
    public Optional<Train> findByNumber(int number) {
        return trainRepository.findByNumber(number);
    }

    @Override
    @Transactional
    public boolean save(Train train) {
        trainRepository.save(train);
        return true;
    }

    @Override
    public boolean findEmptyPlaces(CustomerTicketDTO customerTicketDTO) {
        Train train = trainRepository.findByNumber(customerTicketDTO.getNumber())
                .orElseThrow(TrainNotFoundException::new);
        int place = train.getPlace();
        if (place - train.getTickets().size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<TrainDTO> findAllTrains() {
        List<Train> results = trainRepository.findAll();

        List<TrainDTO> trainDTOList = new ArrayList<>();

        for (Train train : results) {
            TrainDTO trainDTO = new TrainDTO();
            trainDTO.setNumber(train.getNumber());
            trainDTO.setPlace(train.getPlace());
            trainDTOList.add(trainDTO);
        }
        return trainDTOList;
    }

    @Override
    public List<PassengerDTO> findAllPassengers(int id) {
        List<Object[]> results = trainRepository.findALLPassengersByTrainId(find(id));

        List<PassengerDTO> passengerDTOList = new ArrayList<>();

        for (Object[] row : results) {
            PassengerDTO container = new PassengerDTO();
            container.setName((String) row[0]);
            container.setFamily((String) row[1]);
            container.setBirthday((LocalDate) row[2]);
            passengerDTOList.add(container);
        }

        return passengerDTOList;
    }

    @Override
    public List<ScheduleForTrainDTO> findScheduleForTrains() {
        List<ScheduleForTrainDTO> allTrains = new ArrayList<>();
        Train train;
        List<Integer> trains = trainRepository.findAll().stream().map(x -> x.getTrain_id())
                .collect(Collectors.toList());
        for (int i = 0; i < trains.size(); i++) {
            train = trainRepository.findById(trains.get(i)).orElse(null);
            if (train != null) {
                ScheduleForTrainDTO trainDTO = new ScheduleForTrainDTO();
                trainDTO.setNumber(train.getNumber());

                List<Object[]> stations = trainRepository.findScheduleByTrainId(train);

                List<ScheduleDTO> scheduleForTrainDTOList = new ArrayList<>();

                for (Object[] row : stations) {
                    ScheduleDTO schedule = new ScheduleDTO();
                    schedule.setName((String) row[0]);
                    schedule.setTime((LocalDateTime) row[1]);
                    scheduleForTrainDTOList.add(schedule);
                }
                scheduleForTrainDTOList.sort((o1, o2) -> o1.getTime().compareTo(o2.getTime()));
                trainDTO.setStations(scheduleForTrainDTOList);
                allTrains.add(trainDTO);
            }
        }
        return allTrains;
    }

    @Override
    public List<ScheduleForTrainDTO> searchTrains(CustomerRouteDTO customerRouteDTO) {
        List<ScheduleForTrainDTO> allTrains = TrainServiceImpl.this.findScheduleForTrains();
        List<ScheduleForTrainDTO> result = new ArrayList<>();
        ScheduleForTrainDTO train = new ScheduleForTrainDTO();
        for (int i = 0; i < allTrains.size(); i++) {
            List<ScheduleDTO> stations = allTrains.get(i).getStations();
            for (int j = 0; j < stations.size(); j++) {
                if (stations.get(j).getName().equals(customerRouteDTO.getRouteStart())
                        && LocalDate.from(stations.get(j).getTime())
                        .equals(LocalDate.from(customerRouteDTO.getDate()))) {
                    for (int k = j; k < stations.size(); k++) {
                        if (stations.get(k).getName().equals(customerRouteDTO.getRouteEnd())) {
                            train.setStations(List.of(stations.get(j), stations.get(k)));
                            train.setNumber(allTrains.get(i).getNumber());
                            result.add(train);
                        }
                    }
                }
            }
        }
        if (result.isEmpty()) {
            throw new TrainNotFoundException();
        }
        return result;
    }
}
