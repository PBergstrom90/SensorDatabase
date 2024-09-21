package com.example.tempprojekt.controller;

import com.example.tempprojekt.model.Datapoint;
import com.example.tempprojekt.model.Sensor;
import com.example.tempprojekt.service.SensorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping
    public String getAllSensors(Model model) {
        List<Sensor> sensors = sensorService.getAllSensors();
        model.addAttribute("sensors", sensors);
        return "sensor-list";
    }

    @GetMapping("/add")
    public String showAddSensorForm(Model model) {
        model.addAttribute("sensor", new Sensor());
        return "sensor-add";
    }

    @PostMapping("/add")
    public String addSensor(@ModelAttribute Sensor sensor) {
        sensorService.saveSensor(sensor);
        return "redirect:/sensors";
    }

    @GetMapping("/delete/{id}")
    public String deleteSensor(@PathVariable int id) {
        sensorService.deleteSensor(id);
        return "redirect:/sensors";
    }

    // Ny metod för att visa sensorns datapunkter
    @GetMapping("/{id}/datapoints")
    public String viewDatapoints(@PathVariable int id, Model model) {
        Optional<Sensor> sensorOpt = sensorService.getSensorById(id);
        if (sensorOpt.isPresent()) {
            Sensor sensor = sensorOpt.get();
            List<Datapoint> datapoints = sensorService.getDatapointsBySensorId(id);
            model.addAttribute("sensor", sensor);
            model.addAttribute("datapoints", datapoints);
            return "sensor-datapoints";
        } else {
            return "redirect:/sensors";
        }
    }
}
