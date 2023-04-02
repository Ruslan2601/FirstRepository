package com.schedule.rest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "stations")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private int station_id;

    @Column(name = "name")
    @NotEmpty(message = "Station name should not be empty")
    @Size(min = 2, max = 15, message = "Station name should be between 2 and 15 characters")
    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "schedule",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "train_id"))
    private List<Train> trains;

    @OneToMany(mappedBy = "station_id")
    private List<Schedule> schedules;

    public Station(String name) {
        this.name = name;
    }

    public Station() {

    }

    @Override
    public String toString() {
        return "Station{" +
                "station_id=" + station_id +
                ", name='" + name + '\'' +
                ", trains=" + trains +
                ", schedules=" + schedules +
                '}';
    }
}
