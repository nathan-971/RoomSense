class DeviceState:
    def __init__(
            self,
            deviceStateId = 0,
            heaterState = False,
            acState = False,
            mode = None
        ):
            self.deviceStateId = deviceStateId
            self.heaterState = heaterState
            self.acState = acState
            self.mode = mode

    def Print(self):
        print(f"""
            Device State:
            Device ID: {self.deviceStateId}
            Heater State: {self.heaterState}
            Air Con State: {self.acState}
            Mode: {self.mode}
        """)