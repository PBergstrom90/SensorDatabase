import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Line } from 'react-chartjs-2';

// Lägg till och registrera Chart.js-komponenter
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
    Filler, // Lägg till för att kunna fylla under linjen
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
        // Hämta data från backend
        axios.get('/api/sensors/1/datapoints')
            .then(response => {
                const data = response.data;
                setSensorData(data);
            })
            .catch(error => {
                console.error("Error fetching sensor data:", error);
            });
    }, []);

    // Förbereda data för graf med datum (dag och månad) på x-axeln
    const chartData = {
        labels: sensorData.map(dp => new Date(dp.timestamp).toLocaleDateString()), // Datum för x-axel
        datasets: [
            {
                label: 'Temperature',
                data: sensorData.map(dp => dp.value), // Värden för y-axel
                fill: true, // Fyll under linjen
                backgroundColor: 'rgba(255, 255, 0, 0.2)', // Transparent gul fyllning
                borderColor: 'yellow', // Gul linje
                tension: 0.4, // Mjukare kurvor
                borderWidth: 2,
                pointBackgroundColor: 'yellow',
                pointBorderColor: '#fff',
                pointHoverBackgroundColor: '#fff',
                pointHoverBorderColor: 'yellow',
            }
        ]
    };

    // Anpassa utseendet på grafen
    const chartOptions = {
        responsive: true,
        plugins: {
            legend: {
                position: 'top', // Flytta legenden till toppen
                labels: {
                    color: 'yellow', // Färg på legendtexten
                    font: {
                        size: 14,
                        weight: 'bold',
                    },
                }
            },
            tooltip: {
                enabled: true,
                backgroundColor: '#333', // Mörk bakgrund för tooltip
                titleColor: '#fff',
                bodyColor: '#fff',
                borderColor: 'yellow',
                borderWidth: 1,
            }
        },
        scales: {
            x: {
                grid: {
                    display: false, // Döljer rutnät på x-axeln för ett renare utseende
                },
                ticks: {
                    color: 'yellow', // Färg på x-axelns etiketter
                    font: {
                        size: 12,
                        weight: 'bold'
                    },
                }
            },
            y: {
                grid: {
                    color: 'rgba(255, 255, 0, 0.2)', // Färg på rutnätet på y-axeln
                },
                ticks: {
                    color: 'yellow', // Färg på y-axelns etiketter
                    font: {
                        size: 12,
                        weight: 'bold'
                    },
                    beginAtZero: true, // Startar y-axeln vid 0
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
                <p>Ingen data tillgänglig</p>
            )}
        </div>
    );
};

export default Dashboard;
