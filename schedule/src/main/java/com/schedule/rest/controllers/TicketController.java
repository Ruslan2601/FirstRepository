package com.schedule.rest.controllers;

import com.schedule.rest.dto.CustomerTicketDTO;
import com.schedule.rest.services.PassengerService;
import com.schedule.rest.services.TicketService;
import com.schedule.rest.util.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;
    private final TicketValidator ticketValidator;
    private final PassengerService passengerService;

    @Autowired
    public TicketController(TicketService ticketService, TicketValidator ticketValidator, PassengerService passengerService) {
        this.ticketService = ticketService;
        this.ticketValidator = ticketValidator;
        this.passengerService = passengerService;
    }


    @PostMapping
    public CustomerTicketDTO buyTicket(@RequestBody @Valid CustomerTicketDTO customerTicketDTO,
                                       BindingResult bindingResult) {
        ticketValidator.validate(customerTicketDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage()).append(";");
            }
            throw new BuyTicketException(errorMsg.toString());
        }
        int passenger_id = passengerService.saveTicket(customerTicketDTO);
        ticketService.saveTicket(customerTicketDTO, passenger_id);

        return null;
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(BuyTicketException e) {

        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                Instant.now().toString()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(PassengerNotFoundException e) {

        ErrorResponse response = new ErrorResponse(
                "Passenger not found!",
                Instant.now().toString()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
