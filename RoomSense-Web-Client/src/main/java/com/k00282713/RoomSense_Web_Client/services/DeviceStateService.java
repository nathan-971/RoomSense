package com.k00282713.RoomSense_Web_Client.services;

import com.k00282713.RoomSense_Web_Client.entities.DeviceState;
import com.k00282713.RoomSense_Web_Client.entities.dto.DeviceStateDTO;
import com.k00282713.RoomSense_Web_Client.entities.dto.SwitchModeRequest;
import com.k00282713.RoomSense_Web_Client.entities.dto.SwitchModeResponse;
import com.k00282713.RoomSense_Web_Client.entities.enums.Actuator;
import com.k00282713.RoomSense_Web_Client.entities.enums.Mode;
import com.k00282713.RoomSense_Web_Client.repositories.DeviceStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Optional;

@Service
public class DeviceStateService
{
    @Autowired
    private DeviceStateRepository deviceStateRepository;

    private final Integer streamInterval = 1;

    public Mono<DeviceStateDTO> getDeviceState(Integer deviceId)
    {
        return Mono.fromCallable(() -> deviceStateRepository.findById(deviceId))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optionalState -> optionalState
                        .map(state -> Mono.just(mapToDTO(state)))
                        .orElse(Mono.empty()));
    }

    public Flux<DeviceStateDTO> streamDeviceState()
    {
        return Flux.interval(Duration.ofSeconds(1))
                .flatMap(tick -> getDeviceState(1))
                .distinctUntilChanged();
    }

    public SwitchModeResponse switchMode(SwitchModeRequest request, Integer deviceId)
    {
        Mode requestedMode = getModeFromRequest(request);
        SwitchModeResponse response = new SwitchModeResponse();
        if(requestedMode == null)
        {
            response.setMessage("Invalid Mode Inputted");
            response.setSuccess(false);
            return response;
        }

        DeviceState state = deviceStateRepository.findById(deviceId).orElse(null);
        if(state == null)
        {
            response.setMessage("Device Couldn't be Found");
            response.setSuccess(false);
            return response;
        }

        if(state.getMode() == requestedMode)
        {
            response.setMessage("Device is already set too " + requestedMode);
            response.setSuccess(false);
            return response;
        }

        state.setMode(requestedMode);
        deviceStateRepository.save(state);

        response.setMessage(requestedMode == Mode.MANUAL ? "Mode toggled to Manual Actuation" : "Mode toggled to Auto Actuation");
        response.setSuccess(true);
        return response;
    }

    private Mode getModeFromRequest(SwitchModeRequest request)
    {
        Mode mode;
        try
        {
            mode = Mode.valueOf(request.getMode().toUpperCase());
            return mode;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    private DeviceStateDTO mapToDTO(DeviceState deviceState)
    {
        DeviceStateDTO dto = new DeviceStateDTO();
        dto.setDeviceStateId(deviceState.getDeviceStateId());
        dto.setHeaterState(deviceState.getHeaterState());
        dto.setAcState(deviceState.getAcState());
        dto.setMode(deviceState.getMode());

        return dto;
    }
}
