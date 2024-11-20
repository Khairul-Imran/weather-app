export interface WeatherResponse {
    location: Location,
    current: CurrentWeather
    forecast: Forecast
};

export interface Forecast {
    date: string,
    dateEpoch: number,
    dailyForecastData: DailyForecastData[],
    dailyAstroData: DailyAstroData[],
    hourlyForecastData: HourlyForecastData[][]
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

// Forecast
export interface DailyForecastData {
    maxTemp: number,
    minTemp: number,
    avgTemp: number,
    maxWind: number,
    totalPrecipitation: number,
    totalSnowfall: number,
    avgHumidity: number,
    weatherConditionText: string,
    weatherConditionIcon: string,
    chanceOfRain: number,
    chanceOfSnow: number
};

export interface DailyAstroData {
    sunriseTime: string,
    sunsetTime: string,
    moonriseTime: string,
    moonsetTime: string
};

export interface HourlyForecastData {
    dateAndTime: string,
    timeEpoch: number,
    temperature: number,
    wind: number,
    humidity: number,
    feelsLike: number,
    weatherConditionText: string,
    weatherConditionIcon: string,
    chanceOfRain: number,
    chanceOfSnow: number
}
