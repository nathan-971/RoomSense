package com.k00282713.RoomSense_Web_Client.entities;

import com.k00282713.RoomSense_Web_Client.entities.enums.Actuator;
import com.k00282713.RoomSense_Web_Client.entities.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "actuation_command")
public class ActuationCommand
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actuationCommandId;

    @Enumerated(EnumType.STRING)
    private Actuator actuator;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;
    private LocalDateTime  executedAt;

    public ActuationCommand() { }
    public ActuationCommand(
        Integer actuationCommandId,
        Actuator actuator,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime executedAt
    )
    {
        this.actuationCommandId = actuationCommandId;
        this.actuator = actuator;
        this.status = status;
        this.createdAt = createdAt;
        this.executedAt = executedAt;
    }

    public Integer getActuationCommandId()
    {
        return actuationCommandId;
    }

    public void setActuationCommandId(Integer actuationCommandId)
    {
        this.actuationCommandId = actuationCommandId;
    }

    public Actuator getActuator()
    {
        return actuator;
    }

    public void setActuator(Actuator actuator)
    {
        this.actuator = actuator;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExecutedAt()
    {
        return executedAt;
    }

    public void setExecutedAt(LocalDateTime executedAt)
    {
        this.executedAt = executedAt;
    }
}
