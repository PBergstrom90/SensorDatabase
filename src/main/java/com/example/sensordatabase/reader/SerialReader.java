package com.example.sensordatabase.reader;

import com.example.sensordatabase.controller.SensorController;
import com.example.sensordatabase.models.Datapoint;
import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;

public class SerialReader {

    private final long sensorId;
    private final SensorController sensorController; // Reference to the controller

    public SerialReader(int sensorId, SensorController sensorController) {
        this.sensorId = sensorId;
        this.sensorController = sensorController; // Initialize the controller
    }

    boolean isRunning = true;

    public void runSerialReader() throws IOException {
        SerialPort comPort = SerialPort.getCommPort("COM3");
        comPort.setBaudRate(9600);

        if (comPort.openPort()) {
            System.out.println("Port opened successfully.");
        } else {
            System.out.println("Failed to open port.");
            return;
        }

        while (isRunning) {
            try {
                if (comPort.bytesAvailable() > 0) {
                    byte[] readBuffer = new byte[1024];
                    int numRead = comPort.readBytes(readBuffer, readBuffer.length);
                    if (numRead > 0) {
                        String data = new String(readBuffer, 0, numRead).trim();
                        if (!data.isEmpty()) {
                            System.out.println("Received: " + data);
                            // Convert received data to a double value
                            Double value = Double.parseDouble(data);
                            // Create and save a new Datapoint
                            Datapoint datapoint = new Datapoint(value, sensorController.getSensor(sensorId));
                            sensorController.addDatapoint(sensorId, value);
                        }
                    }
                }
            } catch (Exception e) {
                isRunning = false;
                e.printStackTrace();
            }
        }
    }
}