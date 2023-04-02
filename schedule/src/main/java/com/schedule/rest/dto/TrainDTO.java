package com.schedule.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;


@Setter
@Getter
@Accessors(chain = true)
public class TrainDTO {

    @Range(min = 10000, max = 999999, message = "Train number must be six digits")
    @NotNull(message = "Train number should not be empty")
    private int number;

    @Min(value = 1, message = "Number of seats must be greater than zero")
    @NotNull(message = "Number of seats cannot be empty")
    private int place;

    public TrainDTO() {

    }

    public TrainDTO(int number, int place) {
        this.number = number;
        this.place = place;
    }

    @Override
    public String toString() {
        return "TrainDTO{" +
                "number=" + number +
                ", place=" + place +
                '}';
    }
}
