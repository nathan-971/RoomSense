function renderChartFromData(sensorData)
{
    const container = document.getElementById("chartsContainer");
    container.innerHTML = "";

    const labels = sensorData.map(d =>
        new Date(d.timestamp).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    );

    const metrics = [
        { key: "temperature", label: "Temperature (°C)", color: "rgb(255,99,132)" },
        { key: "humidity", label: "Humidity (%)", color: "rgb(54,162,235)" },
        { key: "lightLevel", label: "Light Level", color: "rgb(255,206,86)" },
        { key: "movementDetected", label: "Movement Detected", color: "rgb(75,192,192)" }
    ];

    metrics.forEach(metric => {
        const dataPoints = sensorData.map(d => d[metric.key]);

        const card = document.createElement("div");
        card.className = "card shadow-sm mb-4 rounded";

        const cardBody = document.createElement("div");
        cardBody.className = "card-body";

        const title = document.createElement("h5");
        title.className = "card-title mb-3";
        title.textContent = metric.label;

        const canvas = document.createElement("canvas");

        cardBody.appendChild(title);
        cardBody.appendChild(canvas);
        card.appendChild(cardBody);
        container.appendChild(card);

        const ctx = canvas.getContext("2d");

        new Chart(ctx, {
            type: "line",
            data: {
                labels: labels,
                datasets: [{
                    label: metric.label,
                    data: dataPoints,
                    borderColor: metric.color,
                    backgroundColor: metric.color.replace(")", ", 0.3)"),
                    fill: true,
                    tension: 0.3,
                    borderWidth: 2,
                    pointRadius: 3
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { display: false }
                },
                scales: {
                    y: { beginAtZero: true }
                }
            }
        });
    });
}

function renderLatestState(sensorData)
{
    if (!sensorData || sensorData.length === 0)
    {
         return;
    }

    const latest = sensorData.reduce((a, b) =>
        new Date(a.timestamp) > new Date(b.timestamp) ? a : b
    );

    const container = document.getElementById("latestState");
    container.innerHTML = `
        <div class="card shadow-sm rounded">
            <div class="card-body">
                <h4 class="card-title mb-4">Latest Device State</h4>
                <p><i class="bi bi-clock-fill"></i> <strong>Time:</strong> ${new Date(latest.timestamp).toLocaleString()}</p>
                <p><i class="bi bi-thermometer-half"></i> <strong>Temperature:</strong> ${latest.temperature} °C</p>
                <p><i class="bi bi-droplet-fill"></i> <strong>Humidity:</strong> ${latest.humidity} %</p>
                <p><i class="bi bi-lightbulb-fill"></i> <strong>Light Level:</strong> ${latest.lightLevel}</p>
                <p><i class="bi bi-person-fill"></i> <strong>Movement:</strong> ${latest.movementDetected ? 'Yes' : 'No'}</p>
            </div>
        </div>
    `;
}

function renderControls(deviceState)
 {
    const container = document.getElementById("controlsSection");
    container.innerHTML = `
        <div class="card shadow-sm rounded">
            <div class="card-body">
                <h4 class="card-title mb-4">Device Controls</h4>

                <div class="mb-3">
                    <p><strong>Air Conditioning:</strong> <span class="badge bg-${deviceState.acState ? 'success' : 'secondary'}">${deviceState.acState ? 'ON' : 'OFF'}</span></p>
                    <button class="btn btn-${deviceState.acState ? 'danger' : 'success'} w-100 mb-2" onclick="sendCommand('${deviceState.acState ? 'AC_OFF' : 'AC_ON'}')">
                        Turn ${deviceState.acState ? 'Off' : 'On'}
                    </button>
                </div>

                <div class="mb-3">
                    <p><strong>Heater:</strong> <span class="badge bg-${deviceState.heaterState ? 'success' : 'secondary'}">${deviceState.heaterState ? 'ON' : 'OFF'}</span></p>
                    <button class="btn btn-${deviceState.heaterState ? 'danger' : 'success'} w-100" onclick="sendCommand('${deviceState.heaterState ? 'HEATER_OFF' : 'HEATER_ON'}')">
                        Turn ${deviceState.heaterState ? 'Off' : 'On'}
                    </button>
                </div>
            </div>
        </div>
    `;
}

function sendCommand(command)
{
    fetch("/api/device/command", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ command: command })
    })
    .then(res => res.json())
    .then(data => {
        console.log("Command sent:", data);
    })
    .catch(err => console.error(err));
}

function renderDashboard(sensorData, deviceState)
{
    renderLatestState(sensorData);

    const latest = sensorData.reduce((a, b) =>
        new Date(a.timestamp) > new Date(b.timestamp) ? a : b
    );

    renderControls(deviceState);
    renderChartFromData(sensorData);
}