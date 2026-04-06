from DataLayer.connection import Connection
from Entities.sensorDataContainer import SensorDataContainer

class SensorReadingsRepository:
    def __init__(self, connection: Connection):
        self.db = connection

    def insert(self, data: SensorDataContainer):
        query = """
            INSERT INTO sensor_readings 
            (temperature, humidity, movement_detected, light_level, timestamp)
            VALUES (%s, %s, %s, %s, %s)
        """

        params = (
            data.temperature,
            data.humidity,
            data.movementDetected,
            data.lightLevel,
            data.timestamp
        )

        self.db.Open()
        self.db.Query(query, params)
        self.db.Close()

    def findAll(self):
        query = "SELECT * FROM sensor_readings"

        self.db.Open()
        params = None
        rows = self.db.Query(query, params, fetch = True)
        self.db.Close()

        results = []
        if(rows):
            for row in rows:
                sensorReading = SensorDataContainer(
                    sensorDataId = row.get("sensor_data_id", 0),
                    temperature = row.get("temperature", 0.0),
                    humidity = row.get("humidity", 0.0),
                    movementDetected = bool(row.get("movement_detected", False)),
                    lightLevel = row.get("light_level", 0),
                    timestamp = row.get("timestamp", None)
                )
                results.append(sensorReading)
        return results