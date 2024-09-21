package com.example.tempprojekt.repository;

import com.example.tempprojekt.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
}
