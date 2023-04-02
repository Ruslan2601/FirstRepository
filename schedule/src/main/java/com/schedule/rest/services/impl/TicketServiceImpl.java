package com.schedule.rest.services.impl;

import com.schedule.rest.dto.CustomerTicketDTO;
import com.schedule.rest.models.Passenger;
import com.schedule.rest.models.Ticket;
import com.schedule.rest.repositories.PassengerRepository;
import com.schedule.rest.repositories.TicketRepository;
import com.schedule.rest.services.TicketService;
import com.schedule.rest.util.PassengerNotFoundException;
import com.schedule.rest.util.TrainNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;


    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, PassengerRepository passengerRepository) {
        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Optional<Passenger> findTicket(CustomerTicketDTO customerTicketDTO) {
        return ticketRepository.findTicket(customerTicketDTO.getName(), customerTicketDTO.getFamily(),
                customerTicketDTO.getBirthday(), customerTicketDTO.getNumber());
    }

    @Override
    @Transactional
    public boolean saveTicket(CustomerTicketDTO customerTicketDTO, int passenger_id) {
        Ticket ticket = new Ticket();
        ticket.setPassenger_id(passengerRepository.findById(passenger_id)
               .orElseThrow(PassengerNotFoundException::new));
        ticket.setTrain_id(ticketRepository.findTrainNumber(customerTicketDTO.getNumber())
                .orElseThrow(TrainNotFoundException::new));
        ticketRepository.save(ticket);
        return true;
    }

}
