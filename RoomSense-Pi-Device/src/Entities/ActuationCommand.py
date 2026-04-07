from Entities.Enums.Actuator import Actuator

class ActuationCommand:
    def __init__(
            self,
            actuationCommandId = 0,
            actuator = None,
            status = None,
            createdAt = None,
            executedAt = None
        ):
            self.actuationCommandId = actuationCommandId
            self.actuator = actuator
            self.status = status
            self.createdAt = createdAt
            self.executedAt = executedAt

    def Print(self):
        created_str = self.createdAt.strftime("%Y-%m-%d %H:%M:%S") if self.createdAt else "None"
        executed_str = self.executedAt.strftime("%Y-%m-%d %H:%M:%S") if self.executedAt else "None"

        print(f"""
            Actuator Command:
            ID: {self.actuationCommandId}
            Actuator Device: {self.actuator}
            Command Status: {self.status}
            Created At: {created_str}
            Executed At: {executed_str}
        """)