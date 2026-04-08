package com.k00282713.RoomSense_Web_Client.entities.dto;

public class SwitchModeResponse
{
    private String message;

    public Boolean getSuccess()
    {
        return success;
    }

    public void setSuccess(Boolean success)
    {
        this.success = success;
    }

    private Boolean success;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

}
