from DataLayer.connection import Connection
from Entities.DeviceState import DeviceState

class DeviceStateRepository:
    def __init__(self, connection: Connection):
        self.db = connection

    def findById(self, id):
        query = """
            SELECT * FROM device_state
            WHERE device_state_id = %s
        """

        params = (
            id,
        )

        self.db.Open()
        rows = self.db.Query(query, params, fetch = True)
        self.db.Close()

        if rows:
            result = rows[0]
            deviceState = DeviceState(
                deviceStateId = result.get("device_state_id", 0),
                heaterState = result.get("heater_state", False),
                acState = result.get("ac_state", False),
                mode = result.get("mode", None)
            )

            return deviceState