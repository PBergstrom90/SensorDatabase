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

    // This function helps in making sure that datapoints are added correctly from the serialport,
    // using the SerialReader-class
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
