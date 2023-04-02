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
public class CustomerRouteDTO {

    @NotEmpty(message = "Station name should not be empty")
    @Size(min = 2, max = 15, message = "Station name should be between 2 and 15 characters")
    private String routeStart;

    @NotEmpty(message = "Station name should not be empty")
    @Size(min = 2, max = 15, message = "Station name should be between 2 and 15 characters")
    private String routeEnd;

    @NotNull(message = "Date should not be empty")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate date;

    public CustomerRouteDTO() {

    }

    @Override
    public String toString() {
        return "CustomerRouteDTO{" +
                ", routeStart='" + routeStart + '\'' +
                ", routeEnd='" + routeEnd + '\'' +
                ", date=" + date +
                '}';
    }
}
