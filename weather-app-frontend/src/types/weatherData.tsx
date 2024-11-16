export interface WeatherResponse {
    location: Location,
    current: CurrentWeather
};

export interface Location {
    name: string,
    region: string,
    country: string
};

export interface CurrentWeather {
    lastUpdate: string,
    temperature: number,
    wind: number,
    humidity: number,
    feelsLike: number,
    description: string,
    icon: string
};
