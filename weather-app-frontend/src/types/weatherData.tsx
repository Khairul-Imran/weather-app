export interface WeatherData {
    location: Location,
    current: Current
};

export interface Location {
    name: string,
    region: string,
    country: string
};

export interface Current {
    lastUpdate: string,
    temperature: number,
    wind: number,
    humidity: number,
    feelsLike: number,
    description: string,
    icon: string
};