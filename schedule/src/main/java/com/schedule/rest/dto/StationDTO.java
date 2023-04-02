package com.schedule.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class StationDTO {

    @NotEmpty(message = "Station name should not be empty")
    @Size(min = 2, max = 15, message = "Station name should be between 2 and 15 characters")
    private String name;

}
