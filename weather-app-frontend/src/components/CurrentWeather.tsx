import { WeatherData } from "../services/api/weatherDataApi";
import WeatherDetails from "./WeatherDetails";
import WeatherIcon from "./WeatherIcon";

interface SearchProps {
    data: WeatherData;
}

function CurrentWeather({ data }: SearchProps) {

    const weatherDetails = data.current;
    const weatherIcon = data.current.icon;

    return(
        <div className="max-w-2xl mx-auto mt-8 bg-white rounded-lg shadow-lg overflow-hidden">
            <div className="p-6 bg-gradient-to-r from-blue-500 to-blue-600 text-white">
                <h2 className="text-3xl font-bold">{data.location.name}</h2>
                <div className="text-sm opacity-80">
                    {data.location.region && (
                        <span className="mr-2">{data.location.region},</span>
                    )}
                    <span>{data.location.country}</span>
                </div>
            </div>

            <div className="p-6">
                <div className="flex items-center justify-between mb-8">
                    <WeatherIcon icon={weatherIcon} />
                    <div className="text-right">
                        <div className="text-5xl font-bold text-gray-800">
                            {Math.round(weatherDetails.temperature)}°C
                        </div>
                        <div className="text-gray-500">
                            {weatherDetails.description}
                        </div>
                    </div>
                </div>
                <WeatherDetails details={weatherDetails} />
            </div>
        </div>
    );
}

export default CurrentWeather;
