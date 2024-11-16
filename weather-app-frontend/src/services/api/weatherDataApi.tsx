import { CurrentWeather, Location, WeatherResponse } from "../../types/weatherData";
import { apiClient } from "./baseApi";

// export interface ApiResponse {
//     weatherData: WeatherResponse;
// }

export interface WeatherData {
    location: Location;
    current: CurrentWeather;
}

async function getWeather(searchTerm: string, signal?: AbortSignal): Promise<WeatherData> {
    const response = await apiClient<WeatherResponse>(searchTerm, {
        signal
    });

    return {
        location: response.location,
        current: response.current
    };
}

export const weatherDataApi = {
    getWeather
};