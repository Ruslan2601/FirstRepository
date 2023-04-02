package com.schedule.rest.repositories;

import com.schedule.rest.models.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface TrainRepository extends JpaRepository<Train, Integer> {
    @Transactional(readOnly = true)
    @Query("select p.name, p.family, p.birthday from Ticket t join t.passenger_id p where t.train_id=:id")
    List<Object[]> findALLPassengersByTrainId(Train id);

    @Transactional(readOnly = true)
    @Query("select s2.name, s.time from Schedule s join s.station_id s2 where s.train_id=:id")
    List<Object[]> findScheduleByTrainId(Train id);

    Optional<Train> findByNumber(int number);
}
