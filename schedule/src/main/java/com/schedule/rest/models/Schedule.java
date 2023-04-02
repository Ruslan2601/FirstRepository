package com.schedule.rest.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime time;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "train_id", referencedColumnName = "train_id")
    private Train train_id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "station_id")
    private Station station_id;

    public Schedule() {

    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", time=" + time +
                '}';
    }
}
