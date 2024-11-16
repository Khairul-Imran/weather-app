import { CurrentWeather } from "../types/weatherData";

interface WeatherDetailsProps {
    details: CurrentWeather
}

function WeatherDetails({ details }: WeatherDetailsProps) {


    return(
        <div>
            {details.temperature}
            {details.wind}
            {details.humidity}
            {details.feelsLike}
            {details.description}
            {details.lastUpdate}
        </div>
    );
}

export default WeatherDetails;
