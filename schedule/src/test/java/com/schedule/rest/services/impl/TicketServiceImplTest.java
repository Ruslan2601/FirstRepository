package com.schedule.rest.services.impl;

import com.schedule.rest.dto.CustomerTicketDTO;
import com.schedule.rest.models.Passenger;
import com.schedule.rest.models.Ticket;
import com.schedule.rest.models.Train;
import com.schedule.rest.repositories.PassengerRepository;
import com.schedule.rest.repositories.TicketRepository;
import com.schedule.rest.util.PassengerNotFoundException;
import com.schedule.rest.util.TrainNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TicketServiceImplTest {


    private TicketServiceImpl ticketService;

    private TicketRepository ticketRepository;

    private PassengerRepository passengerRepository;

    @BeforeEach
    public void setup() {
        ticketRepository = Mockito.mock(TicketRepository.class);
        passengerRepository = Mockito.mock(PassengerRepository.class);
        ticketService = new TicketServiceImpl(ticketRepository, passengerRepository);
    }

    @Test
    public void shouldReturnFindTicket() {
        CustomerTicketDTO customerTicketDTO = getTicket();

        Mockito.when(ticketRepository.findTicket(customerTicketDTO.getName(),
                customerTicketDTO.getFamily(), customerTicketDTO.getBirthday(), customerTicketDTO.getNumber()))
                .thenReturn(Optional.of(new Passenger("Viktor", "Vasin",
                        LocalDate.of(1999, 10, 12))));

        Optional<Passenger> passenger = ticketService.findTicket(customerTicketDTO);

        Assertions.assertNotNull(passenger);
    }

    @Test
    public void SaveTicket() {
        Passenger passenger = new Passenger(
                "Viktor", "Vasin", LocalDate.of(1999, 10, 12));

        Mockito.when(passengerRepository.findById(passenger.getPassenger_id())).thenReturn(Optional.of(passenger));

        Mockito.when(ticketRepository.findTrainNumber(555555)).thenReturn(Optional.of(new Train()));

        boolean isTicketSaved = ticketService.saveTicket(getTicket(), 0);

        Assertions.assertTrue(isTicketSaved);
    }

    public CustomerTicketDTO getTicket() {
        CustomerTicketDTO customerTicketDTO = new CustomerTicketDTO();
        customerTicketDTO.setNumber(555555);
        customerTicketDTO.setName("Viktor");
        customerTicketDTO.setFamily("Vasin");
        customerTicketDTO.setBirthday(LocalDate.of(1999, 10, 12));
        customerTicketDTO.setDateNow(
                LocalDateTime.of(2023, 01, 16, 12, 13, 10));
        customerTicketDTO.setDepartureDate(
                LocalDateTime.of(2023, 01, 16, 22, 13, 10));

        return customerTicketDTO;
    }
}
