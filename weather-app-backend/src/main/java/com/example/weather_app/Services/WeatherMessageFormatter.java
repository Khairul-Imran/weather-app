package com.example.weather_app.Services;

import org.springframework.stereotype.Service;

import com.example.weather_app.Models.DailyForecastData;
import com.example.weather_app.Models.WeatherData;

@Service
public class WeatherMessageFormatter {
    
    public String formatCurrentWeather(WeatherData data) {
        return String.format("""

            📍 Current Weather in %s, %s
            
            🌡️ Temperature: %.1f°C
            💨 Wind: %.1f km/h
            💧 Humidity: %d%%
            🌡️ Feels like: %.1f°C
            
            %s

            Last updated: %s

            """,

            data.getLocation().getName(),
            data.getLocation().getCountry(),
            data.getCurrent().getTemperature(),
            data.getCurrent().getWind(),
            data.getCurrent().getHumidity(),
            data.getCurrent().getFeelsLike(),
            data.getCurrent().getDescription(),
            data.getCurrent().getLastUpdate()
        );
    }

    public String formatDailyForecast(WeatherData data) {
        StringBuilder forecast = new StringBuilder("📅 3-Day Forecast:\n\n");

        for (DailyForecastData day : data.getForecast().getDailyForecastData()) {
            forecast.append(String.format("""
                %s
                
                🌡️ High: %.1f°C | Low: %.1f°C
                💧 Rain chance: %d%%

                %s
                
                """,
                // Add date formatting here
                day.getMaxTemp(),
                day.getMinTemp(),
                day.getChanceOfRain(),
                day.getWeatherConditionText()
            ));
        }

        return forecast.toString();
    }
}
