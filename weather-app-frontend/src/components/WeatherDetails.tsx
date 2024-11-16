import { CurrentWeather } from "../types/weatherData";

interface WeatherDetailsProps {
    details: CurrentWeather
}

function WeatherDetails({ details }: WeatherDetailsProps) {


    return(
        <div>
            Temperature: {details.temperature}
            Wind: {details.wind}
            Humidity: {details.humidity}
            Feels Like: {details.feelsLike}
            Description: {details.description}
            Last Updated: {details.lastUpdate}
        </div>
    );
}

export default WeatherDetails;
