package com.k00282713.RoomSense_Web_Client.controllers.api;

import com.k00282713.RoomSense_Web_Client.entities.dto.DeviceStateDTO;
import com.k00282713.RoomSense_Web_Client.entities.dto.SensorReadingDTO;
import com.k00282713.RoomSense_Web_Client.services.DeviceStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/device-state")
public class DeviceStateController
{
    @Autowired
    private DeviceStateService deviceStateService;

    @GetMapping("/get")
    public ResponseEntity<?> getDeviceState()
    {
        Integer device = 1; //Hardcoded DeviceId as for this project I only have 1 Raspberry PI
        DeviceStateDTO deviceState = deviceStateService.getDeviceState(device);
        if(deviceState == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Device State Found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(deviceState);
    }
}
