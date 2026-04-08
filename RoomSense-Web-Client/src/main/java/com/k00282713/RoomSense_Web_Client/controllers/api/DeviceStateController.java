package com.k00282713.RoomSense_Web_Client.controllers.api;

import com.k00282713.RoomSense_Web_Client.entities.dto.DeviceStateDTO;
import com.k00282713.RoomSense_Web_Client.entities.dto.SwitchModeRequest;
import com.k00282713.RoomSense_Web_Client.entities.dto.SwitchModeResponse;
import com.k00282713.RoomSense_Web_Client.services.DeviceStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/device-state")
public class DeviceStateController
{
    @Autowired
    private DeviceStateService deviceStateService;

    @GetMapping(value = "/get")
    public Mono<ResponseEntity<DeviceStateDTO>> getDeviceState()
    {
        Integer device = 1; //Hardcoded DeviceId as for this project I only have 1 Raspberry PI
        return deviceStateService.getDeviceState(device)
                .map(state -> ResponseEntity.ok(state))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<DeviceStateDTO> streamDeviceState()
    {
        return deviceStateService.streamDeviceState();
    }

    @PatchMapping("/switchMode")
    public ResponseEntity<?> switchMode(@RequestBody SwitchModeRequest request)
    {
        Integer device = 1; //Hardcoded DeviceId as for this project I only have 1 Raspberry PI
        SwitchModeResponse response = deviceStateService.switchMode(request, device);
        if(response.getSuccess())
        {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}