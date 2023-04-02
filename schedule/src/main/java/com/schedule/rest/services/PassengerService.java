package com.schedule.rest.services;

import com.schedule.rest.dto.CustomerTicketDTO;


public interface PassengerService {

    int saveTicket(CustomerTicketDTO customerTicketDTO);

}
