package com.example.sensordatabase.reader;

import com.example.sensordatabase.controller.SensorController;
import com.example.sensordatabase.models.Datapoint;
import com.fazecast.jSerialComm.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class SerialReader {

    private final long sensorId;
    private final SensorController sensorController; // Reference to the controller
    private static final Logger logger = LoggerFactory.getLogger(SerialReader.class);
    private SerialPort comPort;

    // Time tracking for data reception
    private Instant lastDataReceivedTime;
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(15); // 15-second timeout

    public SerialReader(int sensorId, SensorController sensorController) {
        this.sensorId = sensorId;
        this.sensorController = sensorController; // Initialize the controller
    }

    boolean isRunning = true;

    public void runSerialReader() throws IOException {
        while (isRunning) {
            if (comPort == null || !comPort.isOpen()) {
                // Try to open the port if it's not already open
                comPort = SerialPort.getCommPort("COM3");
                comPort.setBaudRate(9600);

                if (comPort.openPort()) {
                    logger.info("Port opened successfully.");
                    lastDataReceivedTime = Instant.now(); // Reset the last data received time
                } else {
                    logger.warn("Failed to open port. Retrying...");
                    try {
                        Thread.sleep(5000); // Wait before retrying
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    continue;
                }
            }
            try {
                if (comPort.bytesAvailable() > 0) {
                    byte[] readBuffer = new byte[1024];
                    int numRead = comPort.readBytes(readBuffer, readBuffer.length);
                    if (numRead > 0) {
                        String data = new String(readBuffer, 0, numRead).trim();
                        if (!data.isEmpty()) {
                            logger.info("Received: {}", data);
                            try {
                                // Try to convert received data to a Double value
                                double value = Double.parseDouble(data);
                                // Only save valid double values to the database
                                Datapoint datapoint = new Datapoint(value, sensorController.getSensor(sensorId));
                                sensorController.addDatapoint(sensorId, value);
                                lastDataReceivedTime = Instant.now(); // Update last data received time
                            } catch (NumberFormatException ex) {
                                // Handle case where the value is not a valid Double
                                logger.error("Invalid data received: {}", data);
                                // Continue the loop to try again without stopping the reader
                            }
                        }
                    }
                } else {
                    // Check if we've timed out
                    if (Duration.between(lastDataReceivedTime, Instant.now()).compareTo(TIMEOUT_DURATION) > 0) {
                        logger.warn("No data received for more than {} seconds. Attempting to reconnect...", TIMEOUT_DURATION.getSeconds());
                        reconnectSerialPort();
                    }
                }
            } catch (Exception e) {
                isRunning = false;
                logger.error("An error occurred while reading serial port: ", e);
                reconnectSerialPort();
            }
        }
        comPort.closePort();
        logger.info("Serial port closed.");
    }

    // Method to close and reopen the serial port
    private void reconnectSerialPort() {
        if (comPort != null) {
            comPort.closePort();
            logger.info("Port closed.");
        }

        try {
            Thread.sleep(5000); // Wait for 5 seconds before retrying
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        comPort = SerialPort.getCommPort("COM3");
        comPort.setBaudRate(9600);

        if (comPort.openPort()) {
            logger.info("Port reopened successfully.");
            lastDataReceivedTime = Instant.now(); // Reset last received time
        } else {
            logger.warn("Failed to reopen port. Retrying in 5 seconds...");
        }
    }
}