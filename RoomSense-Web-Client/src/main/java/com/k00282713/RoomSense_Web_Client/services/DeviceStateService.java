package com.k00282713.RoomSense_Web_Client.services;

import com.k00282713.RoomSense_Web_Client.entities.DeviceState;
import com.k00282713.RoomSense_Web_Client.entities.dto.DeviceStateDTO;
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
        return Flux.interval(Duration.ofSeconds(streamInterval)).flatMap(tick -> Flux.defer(() ->
                Flux.fromIterable(deviceStateRepository.findAll())
                        .map(this::mapToDTO)
                        .subscribeOn(Schedulers.boundedElastic())));
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
