package com.schedule.rest.repositories;

import com.schedule.rest.models.Passenger;
import com.schedule.rest.models.Ticket;
import com.schedule.rest.models.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Transactional(readOnly = true)
    @Query("select new com.schedule.rest.models.Passenger(p.name, p.family, p.birthday) from Ticket t join t.passenger_id p" +
            " where p.name=:name and p.family=:family and p.birthday=:birthday and t.train_id.number=:number")
    Optional<Passenger> findTicket(String name, String family, LocalDate birthday, int number);

    @Transactional(readOnly = true)
    @Query("select t from Train t where t.number=:number")
    Optional<Train> findTrainNumber(int number);
}

