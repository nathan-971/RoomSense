package com.k00282713.RoomSense_Web_Client.controllers.api;

import com.k00282713.RoomSense_Web_Client.entities.dto.SensorReadingDTO;
import com.k00282713.RoomSense_Web_Client.services.SensorReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sensor-readings")
public class SensorReadingController
{
    @Autowired
    private SensorReadingService sensorReadingService;

    @GetMapping("/get")
    public ResponseEntity<?> getReadings()
    {
        List<SensorReadingDTO> readings = sensorReadingService.getReadings();
        if(readings == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Data Found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(readings);
    }

    @GetMapping("/getFromLastHour")
    public ResponseEntity<?> getReadingsFromLastHour()
    {
        List<SensorReadingDTO> readings = sensorReadingService.getReadingsFromLastHour();
        if(readings == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Data Found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(readings);
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<SensorReadingDTO> streamSensorReadings()
    {
        return sensorReadingService.streamSensorReadings();
    }
}