package com.k00282713.RoomSense_Web_Client.entities.dto;

public class ActuationCommandResponse
{
    private String result;
    private Boolean success;

    public Boolean getSuccess()
    {
        return success;
    }

    public void setSuccess(Boolean success)
    {
        this.success = success;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }
}
