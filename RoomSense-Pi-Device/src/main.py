import sys
import os

sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), "..")))

from Entities.sensorDataContainer import SensorDataContainer
from DataLayer.connection import Connection
from DataLayer.Repositories.SensorReadingsRepository import SensorReadingsRepository
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

try:
    count = 0

    while True:
        dataContainer = SensorDataContainer(
            temperature = round(sense.get_temperature(), 2),
            humidity = round(sense.get_humidity(), 2),
            movementDetected = False,
            lightLevel = 50,
            timestamp = datetime.now()
        )

        sensorReadingsRepository.insert(dataContainer)

        results = sensorReadingsRepository.findAll()
        for res in results:
            res.Print()

except KeyboardInterrupt:
    GPIO.output(LED_PIN, GPIO.HIGH)
    sense.clear()
    GPIO.cleanup()