function renderChartFromData(sensorData)
{
    const container = document.getElementById("chartsContainer");
    container.innerHTML = "";

    const labels = sensorData.map(d =>
        new Date(d.timestamp).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    );

    const metrics = [
        { key: "temperature", label: "Temperature (°C)", color: "#f1c40f", fill: "#f1c40f80" },
        { key: "humidity", label: "Humidity (%)", color: "#3498db", fill: "#3498db80" },
        { key: "lightLevel", label: "Light Level(%)", color: "#9b59b6", fill: "#9b59b680" },
        { key: "movementDetected", label: "Movement Detected", color: "#e74c3c", fill: "#e74c3c80" }
    ];

    metrics.forEach(metric => {
        const dataPoints = sensorData.map(d => d[metric.key]);

        const card = document.createElement("div");
        card.className = "card shadow-sm ml-2 mb-2 rounded";
        card.style.flex = "0 0 350px";

        const cardBody = document.createElement("div");
        cardBody.className = "card-body";

        const title = document.createElement("h5");
        title.className = "card-title mb-3";
        title.textContent = metric.label;

        const canvas = document.createElement("canvas");
        canvas.style.height = "100%";
        canvas.style.width = "100%";

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
                    backgroundColor: metric.fill,
                    fill: true,
                    tension: 0.3,
                    borderWidth: 2,
                    pointRadius: 3
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
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

function renderLatestState(latestReading)
{
    if (!latestReading)
    {
         return;
    }

    const container = document.getElementById("latestState");
    container.innerHTML = `
        <div class="card-body">
            <h4 class="card-title mb-4">Latest Device State</h4>
            <p><i class="bi bi-clock-fill"></i> <strong>Time:</strong> ${new Date(latestReading.timestamp).toLocaleString()}</p>
            <p><i class="bi bi-thermometer-half"></i> <strong>Temperature:</strong> ${latestReading.temperature} °C</p>
            <p><i class="bi bi-droplet-fill"></i> <strong>Humidity:</strong> ${latestReading.humidity} %</p>
            <p><i class="bi bi-lightbulb-fill"></i> <strong>Light Level:</strong> ${latestReading.lightLevel}</p>
            <p><i class="bi bi-person-fill"></i> <strong>Movement:</strong> ${latestReading.movementDetected ? 'Yes' : 'No'}</p>
        </div>
    `;
}

function renderControls(deviceState)
{
    const container = document.getElementById("controlsSection");
    const MODE = {
        AUTO: 'AUTO',
        MANUAL: 'MANUAL'
    };

    container.innerHTML = `
        <div class="card-body">
            <h4 class="card-title mb-4">Device Controls</h4>
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <strong>Mode:</strong>
                    <span class="badge bg-${deviceState.mode === MODE.AUTO ? 'primary' : 'warning'}">
                        ${deviceState.mode}
                        ${deviceState.mode === MODE.AUTO ? '<i class="bi bi-gear-fill"></i>' : '<i class="bi bi-person-fill"></i>' }
                    </span>
                </div>

                <button
                    class="btn btn-outline-${deviceState.mode === MODE.AUTO ? 'warning' : 'primary'} btn-sm"
                    onclick="toggleMode('${deviceState.mode}')">
                    Switch to ${deviceState.mode === MODE.AUTO ? 'MANUAL' : 'AUTO'}
                </button>
            </div>
            <div class="mb-3">
                <p>
                    <strong>Air Conditioning:</strong>
                    <span class="badge bg-${deviceState.acState ? 'success' : 'secondary'}">${deviceState.acState ? 'ON' : 'OFF'}</span>
                </p>
                <button class="btn btn-${deviceState.acState ? 'danger' : 'success'} w-100 mb-2" value="AC" onclick="sendCommand(this)" ${deviceState.mode === MODE.AUTO ? 'disabled' : ''}>
                    Turn ${deviceState.acState ? 'Off' : 'On'}
                </button>
            </div>

            <div class="mb-3">
                <p>
                    <strong>Heater:</strong>
                    <span class="badge bg-${deviceState.heaterState ? 'success' : 'secondary'}">${deviceState.heaterState ? 'ON' : 'OFF'}</span>
                </p>
                <button class="btn btn-${deviceState.heaterState ? 'danger' : 'success'} w-100" value="HEATER" onclick="sendCommand(this)" ${deviceState.mode === MODE.AUTO ? 'disabled' : ''}>
                    Turn ${deviceState.heaterState ? 'Off' : 'On'}
                </button>
            </div>
        </div>
    `;
}

function renderDashboard(sensorData, latestReading)
{
    renderLatestState(latestReading);
    renderChartFromData(sensorData);
}