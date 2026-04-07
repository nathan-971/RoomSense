from DataLayer.connection import Connection

from Entities.Enums.Actuator import Actuator
from Entities.Enums.Status import Status
from Entities.ActuationCommand import ActuationCommand

class ActuationCommandRepository:
    def __init__(self, connection: Connection):
        self.db = connection

    def update(self, data):
        query = """
            UPDATE actuation_command 
            SET executed_at = %s, status = %s
            WHERE actuation_command_id = %s
        """

        params = (
            data.executedAt,
            data.status.value,
            data.actuationCommandId
        )

        self.db.Open()
        self.db.Query(query, params)
        self.db.Close()

    def findAllQueued(self):
        query = """
            SELECT * FROM actuation_command 
            WHERE status = 'QUEUED'
            ORDER BY created_at
        """

        self.db.Open()
        params = None
        rows = self.db.Query(query, params, fetch = True)
        self.db.Close()

        results = []
        if(rows):
            for row in rows:
                actuationCommand = ActuationCommand(
                    actuationCommandId = row.get("actuation_command_id", 0),
                    actuator = Actuator(row.get("actuator")),
                    status = Status(row.get("status")),
                    createdAt = row.get("created_at", None),
                    executedAt = row.get("executed_at", None)
                )
                results.append(actuationCommand)
        return results