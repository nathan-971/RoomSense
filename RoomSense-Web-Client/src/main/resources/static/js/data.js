let deviceState = null;
let latestReading = null;
let sensorData = [];

document.addEventListener("DOMContentLoaded", async function ()
{
    try
    {
        const initialData = await FetchFromAPI("/api/v1/sensor-readings/getFromLastHour");
        if (initialData && initialData.length > 0)
        {
            sensorData = initialData;
            renderChartFromData(sensorData);
        }

        const initialDeviceState = await FetchFromAPI("/api/v1/device-state/get");
        if (initialDeviceState)
        {
            deviceState = initialDeviceState;
            renderControls(deviceState);
        }

        const sensorSource = new EventSource("/api/v1/sensor-readings/stream");

        sensorSource.onmessage = function(event)
        {
            latestReading = JSON.parse(event.data);

            sensorData.push(latestReading);
            if (sensorData.length > 60) sensorData.shift();

            renderLatestState(latestReading);
            renderChartFromData(sensorData);
        };

        const deviceSource = new EventSource("/api/v1/device-state/stream");
        deviceSource.onmessage = function(event)
        {
            deviceState = JSON.parse(event.data);
            renderControls(deviceState);
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
    };

    fetch("/api/v1/actuation-command/send", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(actuationDevice)
    })
    .then(result => result.json())
    .then(data => {
        if (!data.success) {
            showPopup(data.message || "Command failed");
        }
    })
    .catch(err => {
        showPopup("Network error. Please try again.");
    });
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
    .then(result => result.json())
    .then(data => {
        if (!data.success) {
            showPopup(data.message || "Failed to switch mode");
        }
    })
    .catch(err => {
        showPopup("Network error while switching mode.");
    });
}

async function FetchFromAPI(uri)
{
    try
    {
        const response = await fetch(uri);
        if (!response.ok)
        {
            let errorMessage = "Something went wrong";
            try
            {
                const text = await response.text();
                if (text)
                {
                    errorMessage = text;
                }
            }
            catch (e)
            {
                showPopup
            }
            showPopup(errorMessage);
            return null;
        }
        return await response.json();
    }
    catch (err)
    {
        showPopup("Network error. Please try again.");
        return null;
    }
}