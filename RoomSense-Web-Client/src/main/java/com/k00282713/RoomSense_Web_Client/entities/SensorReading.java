package com.k00282713.RoomSense_Web_Client.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_readings")
public class SensorReading
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sensorDataId;

    private Double temperature;
    private Double humidity;
    private Boolean movementDetected;
    private Integer lightLevel;
    private LocalDateTime timestamp;

    SensorReading() { }
    SensorReading(
            Integer sensorDataId,
            Double temperature,
            Double humidity,
            Boolean movementDetected,
            Integer lightLevel,
            LocalDateTime timestamp
    )
    {
        this.sensorDataId = sensorDataId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.movementDetected = movementDetected;
        this.lightLevel = lightLevel;
        this.timestamp = timestamp;
    }

    public Integer getSensorDataId()
    {
        return sensorDataId;
    }

    public void setSensorDataId(Integer sensorDataId)
    {
        this.sensorDataId = sensorDataId;
    }

    public Double getTemperature()
    {
        return temperature;
    }

    public void setTemperature(Double temperature)
    {
        this.temperature = temperature;
    }

    public Double getHumidity()
    {
        return humidity;
    }

    public void setHumidity(Double humidity)
    {
        this.humidity = humidity;
    }

    public Boolean getMovementDetected()
    {
        return movementDetected;
    }

    public void setMovementDetected(Boolean movementDetected)
    {
        this.movementDetected = movementDetected;
    }

    public Integer getLightLevel()
    {
        return lightLevel;
    }

    public void setLightLevel(Integer lightLevel)
    {
        this.lightLevel = lightLevel;
    }

    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp)
    {
        this.timestamp = timestamp;
    }
}
