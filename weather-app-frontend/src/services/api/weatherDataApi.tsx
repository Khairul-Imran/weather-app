import { CurrentWeather, Forecast, Location, WeatherResponse } from "../../types/weatherData";
import { apiClient } from "./baseApi";

export interface WeatherData {
    location: Location;
    current: CurrentWeather;
    forecast: Forecast
};

async function getWeather(searchTerm: string, signal?: AbortSignal): Promise<WeatherData> {
    const response = await apiClient<WeatherResponse>(searchTerm, {
        signal
    });

    console.log("Received response: ", response);

    return {
        location: response.location,
        current: response.current,
        forecast: response.forecast
    };
}

export const weatherDataApi = {
    getWeather
};
