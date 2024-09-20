package com.example.sensordatabase.repos;

import com.example.sensordatabase.models.Datapoint;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DatapointRepository extends JpaRepository<Datapoint, Long> {
}
