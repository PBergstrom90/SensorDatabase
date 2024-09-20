package com.example.sensordatabase.controller;

import com.example.sensordatabase.models.Datapoint;
import com.example.sensordatabase.models.Sensor;
import com.example.sensordatabase.repos.SensorRepository;
import com.example.sensordatabase.service.SensorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final SensorRepository sensorRepository;

    public SensorController(SensorRepository sensorRepository, SensorService sensorService) {
        this.sensorRepository = sensorRepository;
        this.sensorService = sensorService;
    }

    public Sensor initializeSensor(String sensorName) {
        if (!sensorRepository.findByName(sensorName).isPresent()) {
            Sensor sensor = new Sensor(sensorName);
            return sensorRepository.save(sensor);
        }
        return sensorRepository.findByName(sensorName).get();
    }

    @GetMapping
    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    @PostMapping("/add")
    public Sensor addSensor(@RequestBody Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    public Sensor getSensor(Long sensorId) {
        return sensorRepository.findById(sensorId).orElseThrow(()
                -> new IllegalArgumentException("Sensor not found"));
    }

    @GetMapping("/{sensorId}/datapoints")
    public List<Datapoint> getDatapoints(@PathVariable Long sensorId) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found"));
        return sensor.getDatapoints();
    }

    @PostMapping("/{sensorId}/datapoints/add")
    public List<Datapoint> addDatapoint(@PathVariable Long sensorId, @RequestBody double value) {
        return sensorService.addDatapoint(sensorId, value);
    }
}