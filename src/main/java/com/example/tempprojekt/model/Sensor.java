package com.example.tempprojekt.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 30)
    private String name;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Datapoint> datapoints;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Datapoint> getDatapoints() {
        return datapoints;
    }

    public void setDatapoints(List<Datapoint> datapoints) {
        this.datapoints = datapoints;
    }
}
