package com.example.weather_app.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Forecast {
    
    private String date;
    private int dateEpoch;

    // Will need to get a few days of data for this
    private DailyForecastData[] dailyForecastData;
    private DailyAstroData[] dailyAstroData;
    private HourlyForecastData[][] hourlyForecastData; // Nested array, as you are collecting the hourly data for multiple days

}