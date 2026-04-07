package com.k00282713.RoomSense_Web_Client.services;

import com.k00282713.RoomSense_Web_Client.repositories.ActuationCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActuationCommandService
{
    @Autowired
    private ActuationCommandRepository actuationCommandRepository;
}
