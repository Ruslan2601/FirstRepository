package com.schedule.rest.controllers;

import com.schedule.rest.dto.StationDTO;
import com.schedule.rest.models.Station;
import com.schedule.rest.services.StationService;
import com.schedule.rest.util.ErrorResponse;
import com.schedule.rest.util.StationNotCreatedException;
import com.schedule.rest.util.StationValidator;
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
@RequestMapping("/station")
public class StationController {

    private final StationService stationService;
    private final StationValidator stationValidator;

    @Autowired
    public StationController(StationService stationService, StationValidator stationValidator) {
        this.stationService = stationService;
        this.stationValidator = stationValidator;
    }

    @PostMapping
    public ResponseEntity<StationDTO> newStation(@RequestBody @Valid StationDTO stationDTO,
                                                   BindingResult bindingResult) {
        stationValidator.validate(stationDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage()).append(";");
            }

            throw new StationNotCreatedException(errorMsg.toString());
        }

        Station station = new Station();
        station.setName(stationDTO.getName());
        stationService.save(station);
        return ResponseEntity.ok(stationDTO);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(StationNotCreatedException e) {

        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                Instant.now().toString()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
