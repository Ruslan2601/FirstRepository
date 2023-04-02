package com.schedule.rest.services.impl;

import com.schedule.rest.dto.CustomerTicketDTO;
import com.schedule.rest.repositories.PassengerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceImplTest {

    @InjectMocks
    PassengerServiceImpl passengerService;

    @Mock
    PassengerRepository passengerRepository;

    @Test
    public void saveTicket() {
        CustomerTicketDTO customerTicketDTO = new CustomerTicketDTO();
        customerTicketDTO.setName("Ruslan");
        customerTicketDTO.setFamily("Sorokin");
        customerTicketDTO.setBirthday(LocalDate.of(1990, 6, 13));

        int id = passengerService.saveTicket(customerTicketDTO);

        Assertions.assertEquals(id, 0);
    }
}
