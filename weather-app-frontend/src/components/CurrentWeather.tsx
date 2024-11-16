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
        <div className="mt-8">
            <div>
                Country: {data.location.country}
                Name: {data.location.name}
                Region: {data.location.region}
            </div>
            <WeatherIcon icon={weatherIcon} />
            <WeatherDetails details={weatherDetails} />
        </div>
    );
}

export default CurrentWeather;
