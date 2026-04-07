package com.k00282713.RoomSense_Web_Client.services;

import com.k00282713.RoomSense_Web_Client.entities.DeviceState;
import com.k00282713.RoomSense_Web_Client.entities.dto.DeviceStateDTO;
import com.k00282713.RoomSense_Web_Client.repositories.DeviceStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceStateService
{
    @Autowired
    private DeviceStateRepository deviceStateRepository;

    public DeviceStateDTO getDeviceState(Integer deviceId)
    {
        Optional<DeviceState> foundState = deviceStateRepository.findById(deviceId);
        if(foundState.isPresent())
        {
            return mapToDTO(foundState.get());
        }
        return null;
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
