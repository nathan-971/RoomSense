package com.k00282713.RoomSense_Web_Client.services;

import com.k00282713.RoomSense_Web_Client.entities.SensorReading;
import com.k00282713.RoomSense_Web_Client.entities.dto.SensorReadingDTO;
import com.k00282713.RoomSense_Web_Client.repositories.SensorReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDateTime;
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

    public List<SensorReadingDTO> getReadingsFromLastHour()
    {
        LocalDateTime hourFromNow = LocalDateTime.now().minusHours(1);
        List<SensorReading> readings = sensorReadingRepository.findReadingsFrom(hourFromNow);
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

    public Flux<SensorReadingDTO> streamSensorReadings()
    {
        return Flux.interval(Duration.ofSeconds(1)).
                flatMap(tick -> getLatestSensorReading())
                .distinctUntilChanged(SensorReadingDTO::getTimestamp);
    }

    private Mono<SensorReadingDTO> getLatestSensorReading()
    {
        return Mono.fromCallable(() -> sensorReadingRepository.findTopByOrderByTimestampDesc())
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optionalReading -> optionalReading
                        .map(reading -> Mono.just(mapToDTO(reading)))
                        .orElse(Mono.empty()));
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