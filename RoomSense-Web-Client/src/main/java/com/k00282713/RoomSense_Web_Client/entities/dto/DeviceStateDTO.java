package com.k00282713.RoomSense_Web_Client.entities.dto;

import com.k00282713.RoomSense_Web_Client.entities.enums.Mode;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class DeviceStateDTO
{
    private Integer deviceStateId;
    private Boolean heaterState;
    private Boolean acState;

    @Enumerated(EnumType.STRING)
    private Mode mode;

    public Integer getDeviceStateId()
    {
        return deviceStateId;
    }

    public void setDeviceStateId(Integer deviceStateId)
    {
        this.deviceStateId = deviceStateId;
    }

    public Boolean getHeaterState()
    {
        return heaterState;
    }

    public void setHeaterState(Boolean heaterState)
    {
        this.heaterState = heaterState;
    }

    public Boolean getAcState()
    {
        return acState;
    }

    public void setAcState(Boolean acState)
    {
        this.acState = acState;
    }

    public Mode getMode()
    {
        return mode;
    }

    public void setMode(Mode mode)
    {
        this.mode = mode;
    }
}
