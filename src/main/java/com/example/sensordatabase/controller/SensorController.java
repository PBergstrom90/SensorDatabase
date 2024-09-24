package com.example.sensordatabase.controller;

import com.example.sensordatabase.models.Datapoint;
import com.example.sensordatabase.models.Sensor;
import com.example.sensordatabase.repos.SensorRepository;
import com.example.sensordatabase.service.SensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final SensorRepository sensorRepository;
    private static final Logger logger = LoggerFactory.getLogger(SensorController.class);

    public SensorController(SensorRepository sensorRepository, SensorService sensorService) {
        this.sensorRepository = sensorRepository;
        this.sensorService = sensorService;
    }

    // Method to initialize sensors and handle if it already exists
    public Sensor initializeSensor(String sensorName) {
        logger.info("Initializing sensor: {}", sensorName);
        // Check if the sensor already exists using findByName
        Optional<Sensor> existingSensor = sensorRepository.findByName(sensorName);
        if (existingSensor.isPresent()) {
            logger.info("Sensor already exists: {}", sensorName);
            return existingSensor.get(); // Return the existing sensor
        } else {
            Sensor sensor = new Sensor(sensorName); // Create a new sensor
            logger.info("Sensor created and saved: {}", sensorName);
            return sensorRepository.save(sensor);  // Save the sensor to the database
        }
    }

    @GetMapping
    public List<Sensor> getAllSensors() {
        logger.info("Fetching all sensors");
        return sensorRepository.findAll();
    }

    @PostMapping("/add")
    public Sensor addSensor(@RequestBody Sensor sensor) {
        logger.info("Adding sensor: {}", sensor.getName());
        return sensorRepository.save(sensor);
    }

    public Sensor getSensor(Long sensorId) {
        logger.info("Fetching sensor with ID: {}", sensorId);
        return sensorRepository.findById(sensorId).orElseThrow(() ->
                new IllegalArgumentException("Sensor not found"));
    }

    @GetMapping("/{sensorId}/datapoints")
    public List<Datapoint> getDatapoints(@PathVariable Long sensorId) {
        logger.info("Fetching datapoints for sensor ID: {}", sensorId);
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found"));
        return sensor.getDatapoints();
    }

    @PostMapping("/{sensorId}/datapoints/add")
    public List<Datapoint> addDatapoint(@PathVariable Long sensorId, @RequestBody double value) {
        logger.info("Adding datapoint to sensor ID: {} with value: {}", sensorId, value);
        return sensorService.addDatapoint(sensorId, value);
    }
}
