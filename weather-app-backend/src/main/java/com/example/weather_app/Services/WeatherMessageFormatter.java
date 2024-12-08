package com.example.weather_app.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.example.weather_app.Models.DailyForecastData;
import com.example.weather_app.Models.HourlyForecastData;
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

    // Uses a more compact format
    public String formatHourlyForecast(WeatherData data) {
        StringBuilder hourlyForecast = new StringBuilder();
        hourlyForecast.append(String.format("⏰ Hourly Forecast for %s:\n\n", data.getLocation().getName()));

        HourlyForecastData[][] hourlyDataByDay = data.getForecast().getHourlyForecastData();

        for (int day = 0; day < hourlyDataByDay.length; day++) {
            LocalDateTime date = LocalDateTime.parse(data.getForecast().getDate()).plusDays(day);
            hourlyForecast.append(String.format("📅 %s\n",
                date.format(DateTimeFormatter.ofPattern("EEE, MMM d"))));

            HourlyForecastData[] hoursData = hourlyDataByDay[day];

            // Show every 3 hours in a compact format
            for (int hour = 0; hour < hoursData.length; hour += 3) {
                HourlyForecastData hourData = hoursData[hour];
                LocalDateTime dateTime = LocalDateTime.parse(hourData.getDateAndTime());

                hourlyForecast.append(String.format("%s | %d°C | %d%% rain | %s\n",
                    dateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                    Math.round(hourData.getTemperature()),
                    hourData.getChanceOfRain(),
                    hourData.getWeatherConditionText()
                ));
            }

            hourlyForecast.append("\n");
        }

        return hourlyForecast.toString();
    }
}
