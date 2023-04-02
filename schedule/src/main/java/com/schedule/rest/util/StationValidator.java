package com.schedule.rest.util;

import com.schedule.rest.dto.StationDTO;
import com.schedule.rest.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class StationValidator implements Validator {

    private final StationService stationService;

    @Autowired
    public StationValidator(StationService stationService) {
        this.stationService = stationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return StationDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StationDTO stationDTO = (StationDTO) target;

        if (stationService.findByName(stationDTO.getName()).isPresent()) {
            errors.rejectValue("name", "", "This station already exists");
        }
    }
}
