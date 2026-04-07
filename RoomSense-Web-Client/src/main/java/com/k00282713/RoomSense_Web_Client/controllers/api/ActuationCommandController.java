package com.k00282713.RoomSense_Web_Client.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/actuation-command")
public class ActuationCommandController
{
    @PostMapping("/send")
    public ResponseEntity<?> sendCommand()
    {

    }
}
