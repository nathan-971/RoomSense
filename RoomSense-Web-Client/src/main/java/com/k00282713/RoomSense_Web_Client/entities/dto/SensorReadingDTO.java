package com.k00282713.RoomSense_Web_Client.entities.dto;

import java.time.LocalDateTime;

public class SensorReadingDTO
{
    private Integer sensorDataId;
    private Double temperature;
    private Double humidity;
    private Boolean movementDetected;
    private Integer lightLevel;
    private LocalDateTime timestamp;

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
