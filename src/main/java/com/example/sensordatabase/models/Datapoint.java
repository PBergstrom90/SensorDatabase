package com.example.sensordatabase.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Datapoint {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "temperature")
    private Double value;

    @Column
    @CreationTimestamp
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name="sensor_id", nullable=false)
    @JsonBackReference
    private Sensor sensor;

    public Datapoint(Double value, Sensor sensor) {
        this.value = value;
        this.sensor = sensor;
    }
}
