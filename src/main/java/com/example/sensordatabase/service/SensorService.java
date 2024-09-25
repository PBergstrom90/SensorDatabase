package com.example.sensordatabase.service;

import com.example.sensordatabase.models.Datapoint;
import com.example.sensordatabase.models.Sensor;
import com.example.sensordatabase.repos.SensorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    /* Adds a new datapoint to the specified sensor using data from the SerialReader class.
     Ensures the datapoint is correctly associated with the sensor and saved to the database.
     The @Transactional annotation ensures that the entire operation is completed successfully
     or rolled back in case of any failure, maintaining data integrity. */
    @Transactional
    public List<Datapoint> addDatapoint(Long sensorId, Double value) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found"));

        Datapoint datapoint = new Datapoint(value, sensor);
        sensor.addDatapoint(datapoint);
        sensorRepository.save(sensor);
        return sensor.getDatapoints();
    }
}
