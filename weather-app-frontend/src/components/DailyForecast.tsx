import { DailyForecastData } from "../types/weatherData";

interface DailyForecastProps {
    data: DailyForecastData;
    isSelected: boolean;
    onClick: () => void;
    date: Date;
    dayOffset: number;
}

function DailyForecast({
    data,
    isSelected,
    onClick,
    date,
    dayOffset,
}: DailyForecastProps) {
    const forecastDate = new Date(date);
    forecastDate.setDate(date.getDate() + dayOffset);

    return (
        <button
            onClick={onClick}
            className={`p-4 rounded-lg transition-all ${
                isSelected
                    ? "bg-blue-50 border-2 border-blue-500"
                    : "bg-gray-50 hover:bg-gray-100"
            }`}
        >
            <div className="text-center">
                <div className="mb-2">
                    <p className="font-semibold text-gray-600">
                        {forecastDate.toLocaleDateString("en-US", {
                            weekday: "short",
                        })}
                    </p>
                    <p className="text-sm text-gray-500">
                        {forecastDate.toLocaleDateString("en-US", {
                            month: "short",
                            day: "numeric",
                        })}
                    </p>
                </div>
                <img
                    src={
                        data.weatherConditionIcon.startsWith("//")
                            ? `https:${data.weatherConditionIcon}`
                            : data.weatherConditionIcon
                    }
                    alt={data.weatherConditionText}
                    className="w-16 h-16 mx-auto my-2"
                />
                {/* <div className="flex justify-center gap-2 text-lg font-bold">
                    <span className="text-gray-800">{Math.round(data.maxTemp)}°</span>
                    <span className="text-gray-500">{Math.round(data.minTemp)}°</span>
                </div> */}
                <div className="flex justify-center gap-4 text-lg">
                    <div className="text-center">
                        <span className="text-xs text-gray-500 block">
                            High
                        </span>
                        <span className="font-bold text-gray-800">
                            {Math.round(data.maxTemp)}°
                        </span>
                    </div>
                    <div className="text-center">
                        <span className="text-xs text-gray-500 block">Low</span>
                        <span className="font-bold text-gray-500">
                            {Math.round(data.minTemp)}°
                        </span>
                    </div>
                </div>
                <p className="text-sm text-gray-500 mt-1">
                    {data.weatherConditionText}
                </p>
                <div className="mt-2 text-sm">
                    <div className="flex items-center justify-center gap-1 text-blue-600">
                        <span>💧 {data.chanceOfRain}%</span>
                    </div>
                </div>
            </div>
        </button>
    );
}

export default DailyForecast;
