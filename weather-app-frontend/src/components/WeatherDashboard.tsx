import { useEffect, useState } from "react";
import CurrentWeather from "./CurrentWeather";
import ErrorMessage from "./ErrorMessage";
import LoadingSpinner from "./LoadingSpinner";
import WeatherHeader from "./WeatherHeader";
import WeatherSearch from "./WeatherSearch";
import { useWeatherSearch } from "../hooks/useWeatherSearch";

function WeatherDashboard() {

    // For now this is to just set the default based on the previous search
    // Not sure if I should store the search history, and how
    const [search, setSearch] = useState<string>(() => {
        const previousSearch = localStorage.getItem("previousSearch");
        return previousSearch ? JSON.parse(previousSearch) : "";
    });

    const { weatherData, isLoading, error, searchWeather } = useWeatherSearch();

    // Fix for issue fetching
    const handleSearch = async (location: string) => {
        setSearch(location);
        console.log("Searching for: ", location);
        await searchWeather(location);
    }

    // Save the previous search in localStorage
    // Not sure if I should use this to store search history or not. Currently this only stores the most recent search.
    useEffect(() => {
        localStorage.setItem("previousSearch", JSON.stringify(search));
    }, [search]);

    return(
        <div className="container mx-auto px-4 py-8">
            <WeatherHeader />
            <WeatherSearch onSearch={handleSearch} />s

            {/* Conditionally render these based on state */}
            {isLoading && <LoadingSpinner />}
            {error && <ErrorMessage message={error} />}
            {weatherData && <CurrentWeather data={weatherData} />}
            
        </div>
    );
}

export default WeatherDashboard;
