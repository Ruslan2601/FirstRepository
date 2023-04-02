package com.schedule.rest.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "passengers")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private int passenger_id;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 15, message = "Name should be between 2 and 15 characters")
    private String name;

    @Column(name = "family")
    @NotEmpty(message = "Family should not be empty")
    @Size(min = 2, max = 15, message = "Family should be between 2 and 15 characters")
    private String family;

    @Column(name = "birthday")
    @NotNull(message = "Birthday should not be empty")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthday;

    @OneToMany(mappedBy = "passenger_id")
    private List<Ticket> tickets;

    public Passenger() {

    }

    public Passenger(String name, String family, LocalDate birthday) {
        this.name = name;
        this.family = family;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "passenger_id=" + passenger_id +
                ", name='" + name + '\'' +
                ", family='" + family + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
