package com.schedule.rest.util;

import com.schedule.rest.dto.CustomerTicketDTO;
import com.schedule.rest.services.TicketService;
import com.schedule.rest.services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TicketValidator implements Validator {

    private final TicketService ticketService;
    private final TrainService trainService;

    @Autowired
    public TicketValidator(TicketService ticketService, TrainService trainService) {
        this.ticketService = ticketService;
        this.trainService = trainService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CustomerTicketDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerTicketDTO customerTicketDTO = (CustomerTicketDTO) target;
        if (ticketService.findTicket(customerTicketDTO).isPresent()) {
            errors.rejectValue("name", "", "This passenger is already registered on this train");
        }
        if (!trainService.findEmptyPlaces(customerTicketDTO)) {
            errors.rejectValue("number", "", "There are no empty seats on the train");
        }
        if (customerTicketDTO.getDateNow().plusMinutes(10).isAfter(customerTicketDTO.getDepartureDate())) {
            errors.rejectValue("dateNow", "", "Train departure is at least 10 minutes away");
        }
    }
}
