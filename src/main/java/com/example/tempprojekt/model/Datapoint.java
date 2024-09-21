package com.example.tempprojekt.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Datapoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sensorId", nullable = false)
    private Sensor sensor;

    @Column(nullable = false, length = 30)
    private String tempValue;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateCreated;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public String getTempValue() {
        return tempValue;
    }

    public void setTempValue(String tempValue) {
        this.tempValue = tempValue;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }
}
