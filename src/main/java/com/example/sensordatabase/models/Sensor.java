package com.example.sensordatabase.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Sensor {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;

    @OneToMany(mappedBy="sensor", cascade= CascadeType.ALL)
    @JsonManagedReference
    private List<Datapoint> datapoints;

    public Sensor(String name) {
        this.name = name;
    }

    public void addDatapoint(Datapoint datapoint) {
        datapoints.add(datapoint);
    }
}