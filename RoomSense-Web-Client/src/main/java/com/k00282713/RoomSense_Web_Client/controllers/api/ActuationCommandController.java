package com.k00282713.RoomSense_Web_Client.controllers.api;

import com.k00282713.RoomSense_Web_Client.entities.dto.ActuationCommandRequest;
import com.k00282713.RoomSense_Web_Client.entities.dto.ActuationCommandResponse;
import com.k00282713.RoomSense_Web_Client.services.ActuationCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/actuation-command")
public class ActuationCommandController
{
    @Autowired
    private ActuationCommandService actuationCommandService;

    @PostMapping("/send")
    public ResponseEntity<?> sendCommand(@RequestBody ActuationCommandRequest request)
    {
        Integer device = 1; //Hardcoded DeviceId as for this project I only have 1 Raspberry PI
        ActuationCommandResponse response = actuationCommandService.processCommand(request, device);
        if(response.getSuccess())
        {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
