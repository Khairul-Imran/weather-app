import { CurrentWeather } from "../types/weatherData";

interface WeatherDetailsProps {
    details: CurrentWeather;
}

function WeatherDetails({ details }: WeatherDetailsProps) {
    const stats = [
        {
            label: "Feels Like",
            value: `${Math.round(details.feelsLike)}°C`,
            icon: "🌡️",
        },
        {
            label: "Wind",
            value: `${details.wind} km/h`,
            icon: "💨",
        },
        {
            label: "Humidity",
            value: `${details.humidity}%`,
            icon: "💧",
        },
    ];

    return (
        <div>
            <div className="grid grid-cols-3 gap-4">
                {stats.map((stat) => (
                    <div
                        key={stat.label}
                        className="bg-gray-50 p-4 rounded-lg text-center"
                    >
                        <div className="text-xl mb-1">{stat.icon}</div>
                        <div className="text-sm text-gray-500">
                            {stat.label}
                        </div>
                        <div className="font-semibold text-gray-800">
                            {stat.value}
                        </div>
                    </div>
                ))}
            </div>
            <div className="mt-4 text-sm text-gray-500 text-center">
                Last Updated: {details.lastUpdate} (Local Time)
            </div>
        </div>
    );
}

export default WeatherDetails;
