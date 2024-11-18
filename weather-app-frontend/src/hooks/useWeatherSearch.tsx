import { useCallback, useState } from "react";
import { WeatherResponse } from "../types/weatherData";
import { weatherDataApi } from "../services/api/weatherDataApi";

interface UseWeatherSearchResult {
    weatherData: WeatherResponse | undefined;
    isLoading: boolean;
    error: string | null;
    searchWeather: (location: string) => Promise<void>;
}

// Custom hook
// The way we are fetching data here is based on the user clicking search
// This is not like the automatic fetches for the kanye quotes in todolist -> don't need abort controller, useEffect
export function useWeatherSearch() : UseWeatherSearchResult {
    const [weatherData, setWeatherData] = useState<WeatherResponse>();
    const [isLoading, setIsLoading] = useState(false); // Track loading state
    const [error, setError] = useState<string | null>(null); // Store any errors

    const searchWeather = useCallback(async (location: string) => {
        console.log("useWeatherSearch: Searching for location: ", location);

        // This doesn't seem to ever trigger
        if (!location.trim()) {
            setError("Please enter a location!");
            return;
        }

        setIsLoading(true);
        setError(null);

        try {
            // Make the api call
            const data = await weatherDataApi.getWeather(location);
            console.log("useWeatherSearch: Received data: ", data);
            setWeatherData(data);
        } catch (err) {
            // This is what we are seeing in the browser console
            console.error("useWeatherSearch: Error: ", err);
            setError(err instanceof Error ? err.message : "An error occurred.");
        } finally {
            setIsLoading(false);
        }
    }, []);

    return {
        weatherData,
        isLoading,
        error,
        searchWeather
    };
}