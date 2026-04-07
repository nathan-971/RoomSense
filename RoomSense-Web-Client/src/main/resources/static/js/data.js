document.addEventListener("DOMContentLoaded", async function () {
    try
    {
        const sensorData = await FetchFromAPI("/api/v1/sensor-readings/get");
        const deviceState = await FetchFromAPI("/api/v1/device-state/get");
        if (!sensorData || sensorData.length === 0)
        {
            return;
        }

        if (!deviceState || deviceState.length === 0)
        {
            return;
        }

        renderDashboard(sensorData, deviceState);
    }
    catch (error)
    {
        console.log("Error Fetching Sensor Data", error);
    }
});

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