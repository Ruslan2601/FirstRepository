package com.schedule.rest.services.impl;

import com.schedule.rest.dto.CustomerRouteDTO;
import com.schedule.rest.dto.ScheduleDTO;
import com.schedule.rest.dto.ScheduleForTrainDTO;
import com.schedule.rest.dto.TrainDTO;
import com.schedule.rest.models.Train;
import com.schedule.rest.repositories.TrainRepository;
import com.schedule.rest.services.impl.TrainServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class TrainServiceImplTest {

    @InjectMocks
    private TrainServiceImpl trainService;

    @Mock
    private TrainRepository trainRepository;

    @Test
    public void shouldReturnSearchTrains() {
        CustomerRouteDTO customerRouteDTO = new CustomerRouteDTO();
        customerRouteDTO.setRouteStart("Belgorod");
        customerRouteDTO.setRouteEnd("Moscow");
        customerRouteDTO.setDate(LocalDate.of(2023, 01, 16));

        List<ScheduleForTrainDTO> allTrains = getScheduleForTrains();
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
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Belgorod", result.get(0).getStations().get(0).getName());
    }

    @Test
    public void shouldReturnFindScheduleForTrains() {
        Mockito.when(trainRepository.findAll()).thenReturn(getTrains());

        Train train;
        List<Integer> trains = trainRepository.findAll().stream().map(x -> x.getTrain_id())
                .collect(Collectors.toList());
        for (int i = 0; i < trains.size(); i++) {

            int id = i;
            Mockito.when(trainRepository.findById(trains.get(i))).thenReturn(getTrains().stream()
                    .filter(x -> x.getTrain_id() == id).findFirst());

            train = trainRepository.findById(trains.get(i)).orElse(null);
            Mockito.when(trainRepository.findScheduleByTrainId(train)).thenReturn(getSchedule());

        }
        List<ScheduleForTrainDTO> list = trainService.findScheduleForTrains();

        Assertions.assertEquals(2, list.size());
        Assertions.assertNotNull(list);
    }

    @Test
    public void shouldReturnFindEmptyPlaces() {
        int trainNumber = 555555;
        int tickets = 10;
        boolean isEmptyPlaces = false;

        Mockito.when(trainRepository.findByNumber(trainNumber))
                .thenReturn(Optional.of(new Train(555555, 55)));

        Train train = trainService.findByNumber(trainNumber).orElse(null);

        if (train != null) {
            if (train.getPlace() - tickets > 0) {
                isEmptyPlaces = true;
            }
        }

        Assertions.assertTrue(isEmptyPlaces);
        Assertions.assertNotNull(train);
    }

    @Test
    public void saveTrain() {
        Train train = new Train(55, 555555);

        Mockito.doReturn(train).when(trainRepository).save(train);

        boolean isTrainSaved = trainService.save(train);

        Assertions.assertTrue(isTrainSaved);
    }

    @Test
    public void shouldReturnFind() {
        int trainId = 1;
        Train train = new Train(55555, 55);
        train.setTrain_id(trainId);

        Mockito.when(trainRepository.findById(trainId)).thenReturn(Optional.of(train));

        Train testTrain = trainService.find(trainId);

        Assertions.assertNotNull(testTrain);
        Assertions.assertEquals(train, testTrain);
    }

    @Test
    public void shouldReturnFindByNumber() {
        Train trainOne = new Train(555555, 55);
        int trainNumber = 555555;

        Mockito.when(trainRepository.findByNumber(trainNumber)).thenReturn(Optional.of(trainOne));

        Train train = trainService.findByNumber(trainNumber).orElse(null);

        Assertions.assertNotNull(train);
        Assertions.assertEquals(train, trainOne);
    }

    @Test
    public void shouldReturnFindAllTrains() {
        Mockito.when(trainRepository.findAll()).thenReturn(getTrains());

        List<TrainDTO> trains = trainService.findAllTrains();

        Assertions.assertNotNull(trains);
        Assertions.assertEquals(2, trains.size());
        Assertions.assertEquals(trains.get(0).getNumber(), getTrains().get(0).getNumber());
    }

    private List<Train> getTrains() {
        Train trainOne = new Train();
        Train trainSecond = new Train();

        trainOne.setNumber(555555);
        trainOne.setPlace(55);
        trainOne.setTrain_id(0);

        trainSecond.setNumber(666666);
        trainSecond.setPlace(66);
        trainSecond.setTrain_id(1);

        return List.of(trainOne, trainSecond);
    }

    private List<Object[]> getSchedule() {
        Object[] scheduleOne = new Object[]{"Belgorod",
                LocalDateTime.of(2023, 01, 16, 22, 10, 00)};
        Object[] scheduleSecond = new Object[]{"Moscow",
                LocalDateTime.of(2023, 01, 17, 02, 19, 00)};

        return List.of(scheduleOne, scheduleSecond);
    }

    private List<ScheduleForTrainDTO> getScheduleForTrains() {
        List<ScheduleDTO> scheduleDTOOne = List.of(new ScheduleDTO().setName("Belgorod")
                        .setTime(LocalDateTime.of(2023, 01, 16, 22, 10, 00)),
                new ScheduleDTO().setName("Moscow")
                        .setTime(LocalDateTime.of(2023, 01, 17, 02, 19, 00)));
        List<ScheduleDTO> scheduleDTOSecond = List.of(new ScheduleDTO().setName("Kazan")
                        .setTime(LocalDateTime.of(2023, 01, 16, 22, 10, 00)),
                new ScheduleDTO().setName("Moscow")
                        .setTime(LocalDateTime.of(2023, 01, 17, 02, 19, 00)));
        ScheduleForTrainDTO trainOne = new ScheduleForTrainDTO();
        trainOne.setNumber(555555);
        trainOne.setStations(scheduleDTOOne);

        ScheduleForTrainDTO trainSecond = new ScheduleForTrainDTO();
        trainSecond.setNumber(666666);
        trainSecond.setStations(scheduleDTOSecond);

        return List.of(trainOne, trainSecond);
    }

}
