import WeatherDetails from "./WeatherDetails";
import WeatherIcon from "./WeatherIcon";

interface SearchProps {
    search: string;
}

function CurrentWeather({ search }: SearchProps) {

    

    return(
        <div className="mt-8">
            <div>{/* Location info */}</div>
            <WeatherIcon />
            <WeatherDetails />
        </div>
    );
}

export default CurrentWeather;
