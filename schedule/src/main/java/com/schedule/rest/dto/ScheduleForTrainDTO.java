package com.schedule.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Setter
@Getter
@Accessors(chain = true)
public class ScheduleForTrainDTO {

    @Range(min = 10000, max = 999999, message = "Train number must be six digits")
    @NotNull(message = "Train number should not be empty")
    private int number;

    private List<ScheduleDTO> stations;

    public ScheduleForTrainDTO() {

    }

    public ScheduleForTrainDTO(int number, List<ScheduleDTO> stations) {
        this.number = number;
        this.stations = stations;
    }

    @Override
    public String toString() {
        return "TrainDTO{" +
                "number=" + number +
                ", stations=" + stations +
                '}';
    }
}
