import sys
import os

sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), "..")))

from Entities.SensorDataContainer import SensorDataContainer
from Entities.ActuationCommand import ActuationCommand
from Entities.DeviceState import DeviceState
from Entities.Conditions import Conditions

from Entities.Enums.Actuator import Actuator
from Entities.Enums.Status import Status
from Entities.Enums.Mode import Mode

from DataLayer.connection import Connection
from DataLayer.Repositories.SensorReadingsRepository import SensorReadingsRepository
from DataLayer.Repositories.ActuationCommandRepository import ActuationCommandRepository
from DataLayer.Repositories.DeviceStateRepository import DeviceStateRepository

from sense_hat import SenseHat
from datetime import datetime

from gpiozero import MotionSensor

import RPi.GPIO as GPIO
import time
import ADC0832

# Sensor and Board Setup
sense = SenseHat()
dataContainer = SensorDataContainer()

GPIO.setmode(GPIO.BOARD)

AC_PIN = 11
HEATER_PIN = 12
PIR = MotionSensor(27)

#LDR
ADC0832.setup()

GPIO.setup(AC_PIN, GPIO.OUT)
GPIO.setup(HEATER_PIN, GPIO.OUT)

GPIO.output(AC_PIN, GPIO.LOW)
GPIO.output(HEATER_PIN, GPIO.LOW)

# Data Access Setup
dbCon = Connection()
sensorReadingsRepository = SensorReadingsRepository(dbCon)
actuationCommandRepository = ActuationCommandRepository(dbCon)
deviceStateRepository = DeviceStateRepository(dbCon)

def gatherSensorData():
    return SensorDataContainer(
        temperature = round(sense.get_temperature_from_humidity(), 2),
        humidity = round(sense.get_humidity(), 2),
        movementDetected = 1 if PIR.motion_detected else 0,
        lightLevel = getLightLevel(),
        timestamp = datetime.now()
    )

def handleManualMode(deviceState, currentTime):
    commands = actuationCommandRepository.findAllQueued()
    for cmd in commands:
        if cmd.actuator == Actuator.AC:
            deviceState.acState = not deviceState.acState
            if deviceState.acState :
                GPIO.output(AC_PIN, GPIO.HIGH)
            else:
                GPIO.output(AC_PIN, GPIO.LOW)
            print(f"MANUAL: AC device set to {deviceState.acState}")

        if cmd.actuator == Actuator.HEATER:
            deviceState.heaterState = not deviceState.heaterState
            if deviceState.heaterState:
                GPIO.output(HEATER_PIN, GPIO.HIGH)
            else:
                GPIO.output(HEATER_PIN, GPIO.LOW)
            print(f"MANUAL: Heater device set to {deviceState.heaterState}")
        
        cmd.executedAt = currentTime
        cmd.status = Status.COMPLETED
        actuationCommandRepository.update(cmd)

def handleAutomaticMode(deviceState, sensorData):
    idealTemperature = 20
    idealHumidity = 30

    if sensorData.temperature > idealTemperature or sensorData.humidity > idealHumidity:
        deviceState.acState = True
        GPIO.output(AC_PIN, GPIO.HIGH)
    else:
        deviceState.acState = False
        GPIO.output(AC_PIN, GPIO.LOW)
    print(f"AUTO: AC device set to {deviceState.acState}")

    if sensorData.temperature < idealTemperature:
        deviceState.heaterState = True
        GPIO.output(HEATER_PIN, GPIO.HIGH)
    else:
        deviceState.heaterState = False
        GPIO.output(HEATER_PIN, GPIO.LOW)
    print(f"AUTO: Heater device set to {deviceState.heaterState}")

def getLightLevel():
    rawOutput = ADC0832.getResult()
    rawOutput = max(MIN_LIGHT, min(rawOutput, MAX_LIGHT))
    
    percent = ((rawOutput - MIN_LIGHT) / (MAX_LIGHT - MIN_LIGHT)) * 100
    return max(0, min(100, int(round(percent))))

lastSensorSaveTime = 0
SENSOR_SAVE_INTERVAL = 300 # 5 Minutes

MIN_LIGHT = 5
MAX_LIGHT = 60

try:
    while True:
        currentTime = time.time()
        deviceState = deviceStateRepository.findById(1) # Device ID hardcoded as I only have 1 Raspberry PI
        
        sensorData = gatherSensorData()
        sensorData.Print()

        if deviceState.mode == Mode.MANUAL:
            currentDateTime = datetime.now()
            handleManualMode(deviceState, currentDateTime)
        else:
            handleAutomaticMode(deviceState, sensorData)

        if currentTime - lastSensorSaveTime >= SENSOR_SAVE_INTERVAL:
            sensorReadingsRepository.insert(sensorData)
            print("Sensor Data Saved")
            lastSensorSaveTime = currentTime

        deviceStateRepository.update(deviceState)
        time.sleep(1)
        
except KeyboardInterrupt:
    dbCon.Close()
    sense.clear()
    GPIO.output(AC_PIN, GPIO.HIGH)
    GPIO.output(HEATER_PIN, GPIO.HIGH)
    GPIO.cleanup()