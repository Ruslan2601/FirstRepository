package com.schedule.rest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "trains")
public class Train {

    @Id
    @Column(name = "train_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int train_id;

    @Column(name = "number")
    @Range(min = 10000, max = 999999, message = "Train number must be six digits")
    @NotNull(message = "Train number should not be empty")
    private int number;

    @Column(name = "place")
    @Min(value = 1, message = "Number of seats must be greater than zero")
    @NotNull(message = "Number of seats cannot be empty")
    private int place;

    @OneToMany(mappedBy = "train_id")
    private List<Ticket> tickets;

    @ManyToMany(mappedBy = "trains")
    private List<Station> stations;

    @OneToMany(mappedBy = "train_id")
    private List<Schedule> schedule;

    public Train() {

    }

    public Train(int number, int place) {
        this.number = number;
        this.place = place;
    }

    @Override
    public String toString() {
        return "Train{" +
                "train_id=" + train_id +
                ", number=" + number +
                ", place=" + place +
                '}';
    }
}
