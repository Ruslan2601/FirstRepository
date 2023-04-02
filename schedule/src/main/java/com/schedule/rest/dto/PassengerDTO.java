package com.schedule.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Setter
@Getter
@Accessors(chain = true)
public class PassengerDTO {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 15, message = "Name should be between 2 and 15 characters")
    private String name;

    @NotEmpty(message = "Family should not be empty")
    @Size(min = 2, max = 15, message = "Family should be between 2 and 15 characters")
    private String family;

    @NotNull(message = "Birthday should not be empty")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthday;

}
