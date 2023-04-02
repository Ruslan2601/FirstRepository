package com.schedule.rest.services;

import com.schedule.rest.dto.CustomerTicketDTO;
import com.schedule.rest.models.Passenger;

import java.util.Optional;

public interface TicketService {

    boolean saveTicket(CustomerTicketDTO customerTicketDTO, int passenger_id);

    Optional<Passenger> findTicket(CustomerTicketDTO customerTicketDTO);

}
