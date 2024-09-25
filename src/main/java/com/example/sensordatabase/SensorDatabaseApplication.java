package com.example.sensordatabase;

import com.example.sensordatabase.controller.SensorController;
import com.example.sensordatabase.models.Sensor;
import com.example.sensordatabase.reader.SerialReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class SensorDatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensorDatabaseApplication.class, args);
    }

    /* The ApplicationListener bean is used to set up the SerialReader to start reading data
    from the serial port once the application is fully started. */
    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener(SensorController sensorController) throws Exception {
        return event -> {
            Sensor sensor = sensorController.initializeSensor("TemperatureSensor");
            SerialReader serialReader = new SerialReader(1, sensorController); // Use the correct sensorId
            new Thread(() -> {
                try {
                    serialReader.runSerialReader();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        };
    }
}
