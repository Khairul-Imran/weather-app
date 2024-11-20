package com.example.weather_app.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyForecastData {
    
    // All in celsius
    private double maxTemp;
    private double minTemp;
    private double avgTemp;

    private double maxWind; // km/h
    private double totalPrecipitation; // in mm
    private double totalSnowfall; // in cm
    private int avgHumidity; // in %

    private String weatherConditionText;
    private String weatherConditionIcon;

    private int chanceOfRain; // in %
    private int chanceOfSnow; // in %
}
