package com.example.tempprojekt.service;

import com.example.tempprojekt.model.Datapoint;
import com.example.tempprojekt.model.Sensor;
import com.example.tempprojekt.repository.DatapointRepository;
import com.example.tempprojekt.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;
    private final DatapointRepository datapointRepository;

    public SensorService(SensorRepository sensorRepository, DatapointRepository datapointRepository) {
        this.sensorRepository = sensorRepository;
        this.datapointRepository = datapointRepository;
    }

    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    public Optional<Sensor> getSensorById(int id) {
        return sensorRepository.findById(id);
    }

    public Sensor saveSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    public void deleteSensor(int id) {
        sensorRepository.deleteById(id);
    }

    public List<Datapoint> getDatapointsBySensorId(int sensorId) {
        return datapointRepository.findBySensorId(sensorId);
    }

    public Datapoint saveDatapoint(Datapoint datapoint) {
        return datapointRepository.save(datapoint);
    }
}
