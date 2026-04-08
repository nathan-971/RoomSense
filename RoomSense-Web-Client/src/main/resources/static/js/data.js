document.addEventListener("DOMContentLoaded", async function () {
    try
    {
        const sensorData = await FetchFromAPI("/api/v1/sensor-readings/get");
        if (!sensorData || sensorData.length === 0)
        {
            return;
        }
        renderDashboard(sensorData);

        const initialDeviceState = await FetchFromAPI("/api/v1/device-state/get");
        if (initialDeviceState)
        {
            renderControls(initialDeviceState);
        }

        const deviceStateSource = new EventSource("/api/v1/device-state/stream");
        console.log(deviceStateSource);
        deviceStateSource.onmessage = function(event)
        {
            const deviceState = JSON.parse(event.data);
            renderControls(deviceState);
        };

        deviceStateSource.onerror = function(err)
        {
            console.error("Device state SSE Error", err);
        };
    }
    catch (error)
    {
        console.log("Error Fetching Data", error);
    }
});

function sendCommand(button)
{
    const actuationDevice = {
        actuator: button.value
    }

    fetch("/api/v1/actuation-command/send", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(actuationDevice)
    })
    .catch(err => console.error(err));
}

function toggleMode(currentDeviceMode)
{
    fetch('/api/v1/device-state/switchMode', {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            mode: currentDeviceMode === 'AUTO' ? 'MANUAL' : 'AUTO'
        })
    })
    .catch(err => console.error(err));
}

async function FetchFromAPI(uri)
{
    const response = await fetch(uri);
    if (!response.ok)
     {
        console.log("No Data Found");
        return null;
    }
    return await response.json();
}