import { useCallback, useState } from "react";
import { WeatherResponse } from "../types/weatherData";
import { weatherDataApi } from "../services/api/weatherDataApi";
import { ApiError } from "../services/api/baseApi";

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
            console.error("Search error:", err); // New - To see the full error

            if (err instanceof ApiError) {
                // Now this should catch the 400 status errors
                if (err.status === 400) {
                    if (err.code === 1006) {
                        setError(`We couldn't find "${location}". Please check the spelling or try a different location.`);
                    } else {
                        setError(`Invalid location: ${err.message}`);
                    }
                } else if (err.status >= 500) {
                    setError("Unable to fetch weather data. Please try again later.");
                } else {
                    setError(err.message || "An unexpected error occurred");
                }
            } else {
                setError("Unable to fetch weather data. Please try again later.");
            }

            // if (err instanceof ApiError) {
            //     // Handle the different error codes (reference to WeatherApi docs)
            //     switch (err.code) {
            //         case 1006:
            //             setError(`We couldn't find "${location}". Please check the spelling or try a different location.`);
            //             break;
            //         case 1002:
            //             setError("API key not provided. Please contact support.");
            //             break;
            //         case 1005:
            //             setError("Request URL is invalid. Please try again.");
            //             break;
            //         default:
            //             if (err.status === 429) {
            //                 setError("Too many requests. Please try again later.");
            //             } else if (err.status >= 500) {
            //                 setError("Unable to fetch weather data. Please try again later. hihi motherfucker");
            //             } else {
            //                 setError(err.message || "An unexpected error occurred.");
            //             }
            //     }
            // } else {
            //     setError("Unable to fetch weather data. Please try again later.");
            // }


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
