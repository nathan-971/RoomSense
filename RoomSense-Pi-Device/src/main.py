import sys
import os

sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), "..")))

from Entities.SensorDataContainer import SensorDataContainer
from Entities.ActuationCommand import ActuationCommand
from Entities.DeviceState import DeviceState

from DataLayer.connection import Connection
from DataLayer.Repositories.SensorReadingsRepository import SensorReadingsRepository
from DataLayer.Repositories.ActuationCommandRepository import ActuationCommandRepository
from DataLayer.Repositories.DeviceStateRepository import DeviceStateRepository

from sense_hat import SenseHat
from datetime import datetime

import RPi.GPIO as GPIO
import time

# Sensor and Board Setup
sense = SenseHat()
dataContainer = SensorDataContainer()

LED_PIN = 11

GPIO.setmode(GPIO.BOARD)
GPIO.setup(LED_PIN, GPIO.OUT)
GPIO.output(LED_PIN, GPIO.HIGH)

# Data Access Setup
dbCon = Connection()
sensorReadingsRepository = SensorReadingsRepository(dbCon)
actuationCommandRepository = ActuationCommandRepository(dbCon)
deviceStateRepository = DeviceStateRepository(dbCon)

try:
    commands = actuationCommandRepository.findAll()
    deviceState = deviceStateRepository.findById(1) # ID Is hardcoded 1 as I only have one Raspberry PI

    deviceState.Print()

    for c in commands:
        c.Print()
    
except KeyboardInterrupt:
    dbCon.Close()
    GPIO.output(LED_PIN, GPIO.HIGH)
    sense.clear()
    GPIO.cleanup()