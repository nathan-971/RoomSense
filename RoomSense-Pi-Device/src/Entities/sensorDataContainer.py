class SensorDataContainer:
    def __init__(
        self, 
        sensorDataId = 0, 
        temperature = 0.0, 
        humidity = 0.0, 
        movementDetected = False, 
        lightLevel = 0, 
        timestamp = None
    ):
        self.sensorDataId = sensorDataId
        self.temperature = temperature
        self.humidity = humidity
        self.movementDetected = movementDetected
        self.lightLevel = lightLevel
        self.timestamp = timestamp

    def Print(self):
        print(f"""
            Sensor Data:
            ID: {self.sensorDataId}
            Temperature: {self.temperature} °C
            Humidity: {self.humidity} %
            Movement Detected: {self.movementDetected}
            Light Level: {self.lightLevel}
            Timestamp: {self.timestamp}
        """)