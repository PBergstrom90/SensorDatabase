# Sensor Database

## Group Members

- Edvin Hansson
- Jesper Persson
- Pontus Bergström

## Project Overview

This project is a Spring Boot application designed to interact with temperature sensors. It reads data from sensors through a serial connection, stores the data in a database, and provides a REST API for retrieving sensor information and temperature datapoints.

The application uses **jSerialComm** to manage serial communication, with robust handling of connection issues and data parsing. Sensors are represented in the database, and temperature readings (datapoints) are linked to each sensor for easy retrieval.

## Technologies Used

- **Spring Boot** (Version 3.3.3: Framework for building Java-based web apps and microservices.)
- **Java** (Version 17 or later: Backend programming language for robust server-side logic.)
- **C** (Programming language for Arduino to read sensor data and send it to the backend.)
- **MySQL** (Relational database for storing and managing sensor data.)
- **jSerialComm** (Java library for serial communication with sensors.)
- **Lombok** (Java library to reduce boilerplate code.)
- **Hibernate** (ORM framework for mapping Java objects to database tables.)
- **Gradle** (Build automation tool.)
- **Javascript/React App** (Frontend stack for a dynamic data presentation.)

## Application Structure

- **Main Class:** `SensorDatabaseApplication.java`
- **Controller:** Handles API requests related to sensors and datapoints.
- **Service Layer:** Manages business logic like adding datapoints and interacting with the database.
- **Repository Layer:** Provides data access using Spring Data JPA.
- **SerialReader:** Reads data from a serial port, processes it, and sends it to the database.

## Features

- **Serial Port Integration:** Reads data from a serial port and parses temperature readings.
- **Database Storage:** Stores sensor information and their corresponding datapoints.
- **REST API:** Provides endpoints to interact with sensor data.
- **Logging:** Detailed logging for debugging and monitoring purposes.

## Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone <https://github.com/PBergstrom90/SensorDatabase>

2. **Configure the database**:
- Set up a MySQL (or other) database.
- Update the application.properties file with the database URL, username, and password.

3. **Run the application**:
- Run the Main class `SensorDatabaseApplication.java`.

4. **Serial Communication**:
- Make sure that the Arduino_C application is uploaded and running on an Arduino device (See folder “`Arduino_C`”).
- Ensure that the sensor is connected to the correct serial port (e.g, `COM3`).
- Adjust the serial port settings in the `SerialReader` class if needed.

5. **Run the frontend script**:
- Run the React app script for frontend presentation of the collected data (See folder “`frontend`”).

## ER Diagram

![Application Screenshot](resources/ER%20Diagram.png)