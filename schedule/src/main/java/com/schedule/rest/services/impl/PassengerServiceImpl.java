package com.schedule.rest.services.impl;

import com.schedule.rest.dto.CustomerTicketDTO;
import com.schedule.rest.models.Passenger;
import com.schedule.rest.repositories.PassengerRepository;
import com.schedule.rest.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    @Transactional
    public int saveTicket(CustomerTicketDTO customerTicketDTO) {
        Passenger passenger = new Passenger(customerTicketDTO.getName(), customerTicketDTO.getFamily(),
                customerTicketDTO.getBirthday());
        passengerRepository.save(passenger);
        return passenger.getPassenger_id();
    }
}
