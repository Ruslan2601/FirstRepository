package com.schedule.rest.util;

import com.schedule.rest.dto.TrainDTO;
import com.schedule.rest.services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TrainValidator implements Validator {

    private final TrainService trainService;

    @Autowired
    public TrainValidator(TrainService trainService) {
        this.trainService = trainService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TrainDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TrainDTO trainDTO = (TrainDTO) target;

        if (trainService.findByNumber(trainDTO.getNumber()).isPresent()) {
            errors.rejectValue("number", "", "This train number already exists");
        }
    }
}
