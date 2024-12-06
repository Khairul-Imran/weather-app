import { HourlyForecastData } from "../types/weatherData";

interface HourlyForecastProps {
    data: HourlyForecastData[];
    isVisible: boolean;
}

function HourlyForecast({ data, isVisible }: HourlyForecastProps) {
    if (!isVisible) return null;

    return (
        <div className="p-6">
            <h3 className="text-lg font-semibold text-gray-800 mb-4">
                Hourly Forecast
            </h3>
            <div className="overflow-x-auto">
                <div className="flex gap-4 pb-4">
                    {data.map((hour, index) => (
                        <div
                            key={index}
                            className="flex flex-col items-center min-w-[80px]"
                        >
                            <span className="text-sm text-gray-500">
                                {new Date(hour.dateAndTime).getHours()}:00
                            </span>
                            <img
                                src={
                                    hour.weatherConditionIcon.startsWith("//")
                                        ? `https:${hour.weatherConditionIcon}`
                                        : hour.weatherConditionIcon
                                }
                                alt={hour.weatherConditionText}
                                className="w-8 h-8 my-2"
                            />
                            <span className="font-semibold">
                                {Math.round(hour.temperature)}°
                            </span>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default HourlyForecast;
