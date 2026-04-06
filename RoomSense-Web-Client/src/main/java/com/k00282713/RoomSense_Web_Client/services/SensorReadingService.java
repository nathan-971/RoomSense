package com.k00282713.RoomSense_Web_Client.services;

import com.k00282713.RoomSense_Web_Client.entities.SensorReading;
import com.k00282713.RoomSense_Web_Client.entities.dto.SensorReadingDTO;
import com.k00282713.RoomSense_Web_Client.repositories.SensorReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SensorReadingService
{
    @Autowired
    private SensorReadingRepository sensorReadingRepository;

    public List<SensorReadingDTO> getReadings()
    {
        List<SensorReading> readings = sensorReadingRepository.findAll();
        if(!readings.isEmpty())
        {
            List<SensorReadingDTO> dtoReadings = new ArrayList<>();
            for(SensorReading reading : readings)
            {
                dtoReadings.add(mapToDTO(reading));
            }
            return dtoReadings;
        }
        return null;
    }

    private SensorReadingDTO mapToDTO(SensorReading reading)
    {
        SensorReadingDTO dto = new SensorReadingDTO();
        dto.setSensorDataId(reading.getSensorDataId());
        dto.setTemperature(reading.getTemperature());
        dto.setHumidity(reading.getHumidity());
        dto.setMovementDetected(reading.getMovementDetected());
        dto.setLightLevel(reading.getLightLevel());
        dto.setTimestamp(reading.getTimestamp());

        return dto;
    }
}