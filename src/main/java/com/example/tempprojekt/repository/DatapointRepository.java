package com.example.tempprojekt.repository;

import com.example.tempprojekt.model.Datapoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DatapointRepository extends JpaRepository<Datapoint, Integer> {
    List<Datapoint> findBySensorId(int sensorId);
}
