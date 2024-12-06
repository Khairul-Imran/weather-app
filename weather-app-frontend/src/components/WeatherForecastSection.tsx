import { useState } from "react";
import { WeatherData } from "../services/api/weatherDataApi";
import DailyForecast from "./DailyForecast";
import HourlyForecast from "./HourlyForecast";
import AstroData from "./AstroData";

interface ForecastSectionProps {
    forecastData: WeatherData
}

function WeatherForecastSection({ forecastData }: ForecastSectionProps) {

    const dailyForecastData = forecastData.forecast.dailyForecastData;
    const hourlyForecastData = forecastData.forecast.hourlyForecastData;
    const dailyAstroData = forecastData.forecast.dailyAstroData;

    const [selectedDay, setSelectedDay] = useState<number>(0);

    return (

        <div className="max-w-2xl mx-auto mt-8">
            <div className="bg-white rounded-lg shadow-lg overflow-hidden">
                {/* Daily forecasts */}
                <div className="grid grid-cols-3 gap-4 p-6">
                    {dailyForecastData.map((day, index) => (
                        <DailyForecast
                            key={index}
                            data={day}
                            isSelected={selectedDay === index}
                            onClick={() => setSelectedDay(index)}
                            date={new Date(forecastData.forecast.date)} // Add days based on index
                            dayOffset={index}
                        />
                    ))}
                </div>

                {/* Show hourly forecast for selected day */}
                <div className="border-t border-gray-100">
                    <HourlyForecast 
                        data={hourlyForecastData[selectedDay]}
                        isVisible={true}
                    />
                </div>

                {/* Astro data for the selected day */}
                <div className="border-t border-gray-100">
                    <AstroData data={dailyAstroData[selectedDay]} />
                </div>
            </div>
        </div>

    );
}

export default WeatherForecastSection;