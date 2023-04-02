package com.schedule.rest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int ticket_id;

    @JsonIgnore
    @ManyToOne
    @NotNull(message = "Train field cannot be empty")
    @JoinColumn(name = "train_id", referencedColumnName = "train_id")
    private Train train_id;

    @JsonIgnore
    @ManyToOne
    @NotNull(message = "Passenger field cannot be empty")
    @JoinColumn(name = "passenger_id", referencedColumnName = "passenger_id")
    private Passenger passenger_id;

    public Ticket() {

    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticket_id=" + ticket_id +
                ", train_id=" + train_id +
                ", passenger_id=" + passenger_id +
                '}';
    }
}

