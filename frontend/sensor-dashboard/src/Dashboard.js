import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Line } from 'react-chartjs-2';

// Add and register Chart.js components
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
    Filler, // Added to enable filling under the line
} from 'chart.js';

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
    Filler
);

const Dashboard = () => {
    const [sensorData, setSensorData] = useState([]);

    useEffect(() => {
        // Fetch data from backend
        axios.get('/api/sensors/1/datapoints')
            .then(response => {
                const data = response.data;
                setSensorData(data);
            })
            .catch(error => {
                console.error("Error fetching sensor data:", error);
            });
    }, []);

    // Prepare data for the chart with date (day and month) on the x-axis
    const chartData = {
        labels: sensorData.map(dp => {
            const date = new Date(dp.timestamp);
            const formattedDate = date.toLocaleDateString('sv-SE'); // Get local Swedish time.
            const formattedTime = date.toLocaleTimeString('sv-SE', {hour12: false}); // Get time in 24-hour format.
            return `${formattedDate} ${formattedTime}`; // Combine date and time for the x-axis label
        }),
        datasets: [
            {
                label: 'Temperature',
                data: sensorData.map(dp => dp.value), // Values for the y-axis
                fill: true, // Fill under the line
                backgroundColor: 'rgba(255, 255, 0, 0.2)', // Transparent yellow fill
                borderColor: 'yellow', // Yellow line
                tension: 0.4, // Smoother curves
                borderWidth: 2,
                pointBackgroundColor: 'yellow',
                pointBorderColor: '#fff',
                pointHoverBackgroundColor: '#fff',
                pointHoverBorderColor: 'yellow',
            }
        ]
    };

    // Customize the appearance of the chart
    const chartOptions = {
        responsive: true,
        plugins: {
            legend: {
                position: 'top', // Move the legend to the top
                labels: {
                    color: 'yellow', // Color of the legend text
                    font: {
                        size: 14,
                        weight: 'bold',
                    },
                }
            },
            tooltip: {
                enabled: true,
                backgroundColor: '#333', // Dark background for the tooltip
                titleColor: '#fff',
                bodyColor: '#fff',
                borderColor: 'yellow',
                borderWidth: 1,
            }
        },
        scales: {
            x: {
                grid: {
                    display: false, // Hide grid on the x-axis for a cleaner look
                },
                ticks: {
                    color: 'yellow', // Color of the x-axis labels
                    font: {
                        size: 12,
                        weight: 'bold'
                    },
                }
            },
            y: {
                grid: {
                    color: 'rgba(255, 255, 0, 0.2)', // Color of the grid on the y-axis
                },
                ticks: {
                    color: 'yellow', // Color of the y-axis labels
                    font: {
                        size: 12,
                        weight: 'bold'
                    },
                    beginAtZero: true, // Start the y-axis at 0
                }
            }
        }
    };

    return (
        <div style={{ color: 'yellow', backgroundColor: '#1a1a1a', padding: '20px', fontFamily: 'Arial, sans-serif' }}>
            <h1 style={{ fontSize: '36px', marginBottom: '20px', textShadow: '2px 2px 4px #000' }}>Sensor Dashboard</h1>
            {sensorData.length > 0 ? (
                <Line data={chartData} options={chartOptions} />
            ) : (
                <p>No data available</p>
            )}
        </div>
    );
};

export default Dashboard;
