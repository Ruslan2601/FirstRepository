package com.schedule.rest.controllers;

import com.schedule.rest.dto.*;
import com.schedule.rest.models.Train;
import com.schedule.rest.services.TrainService;
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
@RequestMapping("/train")
public class TrainController {

    private final TrainService trainService;
    private final TrainValidator trainValidator;

    @Autowired
    public TrainController(TrainService trainService, TrainValidator trainValidator) {
        this.trainService = trainService;
        this.trainValidator = trainValidator;
    }

    @GetMapping("/{id}/passengers")
    public List<PassengerDTO> getAllPassengers(@PathVariable("id") int id) {
        return trainService.findAllPassengers(id);
    }

    @GetMapping("allTrains")
    public List<TrainDTO> gelAllTrains() {
        return trainService.findAllTrains();
    }

    @GetMapping("/schedule")
    public List<ScheduleForTrainDTO> getSchedule() {
        return trainService.findScheduleForTrains();
    }

    @PostMapping
    public ResponseEntity<TrainDTO> newTrain(@RequestBody @Valid TrainDTO trainDTO,
                                             BindingResult bindingResult) {
        trainValidator.validate(trainDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage()).append(";");
            }
            throw new TrainNotCreatedException(errorMsg.toString());
        }

        Train train = new Train(trainDTO.getNumber(), trainDTO.getPlace());

        trainService.save(train);
        return ResponseEntity.ok(trainDTO);
    }

    @PostMapping("/search")
    public List<ScheduleForTrainDTO> searchTrain(@RequestBody @Valid CustomerRouteDTO customerRouteDTO,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage()).append(";");
            }
            throw new SearchErrorException(errorMsg.toString());
        }
        return trainService.searchTrains(customerRouteDTO);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SearchErrorException e) {

        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                Instant.now().toString()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(TrainNotCreatedException e) {

        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                Instant.now().toString()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(TrainNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Train not found!",
                Instant.now().toString()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
