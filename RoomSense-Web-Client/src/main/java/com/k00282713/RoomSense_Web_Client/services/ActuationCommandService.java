package com.k00282713.RoomSense_Web_Client.services;

import com.k00282713.RoomSense_Web_Client.entities.ActuationCommand;
import com.k00282713.RoomSense_Web_Client.entities.dto.ActuationCommandRequest;
import com.k00282713.RoomSense_Web_Client.entities.dto.ActuationCommandResponse;
import com.k00282713.RoomSense_Web_Client.entities.enums.Actuator;
import com.k00282713.RoomSense_Web_Client.entities.enums.Status;
import com.k00282713.RoomSense_Web_Client.repositories.ActuationCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ActuationCommandService
{
    @Autowired
    private ActuationCommandRepository actuationCommandRepository;

    public ActuationCommandResponse processCommand(ActuationCommandRequest request)
    {
        Actuator actuator = getActuatorFromRequest(request);
        ActuationCommandResponse response = new ActuationCommandResponse();
        if(actuator == null)
        {
            response.setResult("Invalid Actuator Inputted");
            response.setSuccess(false);
            return response;
        }

        ActuationCommand command = new ActuationCommand();
        command.setActuator(actuator);
        command.setStatus(Status.QUEUED);
        command.setCreatedAt(LocalDateTime.now());

        actuationCommandRepository.save(command);
        response.setResult(actuator == Actuator.AC ? "Air Conditioning Unit Toggled" : "Heating Unit Toggled");
        response.setSuccess(true);
        return response;
    }

    public Actuator getActuatorFromRequest(ActuationCommandRequest request)
    {
        Actuator actuator;
        try
        {
            actuator = Actuator.valueOf(request.getActuator().toUpperCase());
            return actuator;
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}