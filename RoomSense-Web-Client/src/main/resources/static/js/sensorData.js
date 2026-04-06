document.addEventListener("DOMContentLoaded", async function () {
    try
     {
        const response = await fetch("/api/v1/sensorreadings/get");
        const data = response.json();
        if (response.ok)
        {
            console.log(data);
        }
        else
        {
            console.log("No Data Found");
        }
    }
    catch (error)
    {
        console.log("Error Fetching Sensor Data");
    }
});