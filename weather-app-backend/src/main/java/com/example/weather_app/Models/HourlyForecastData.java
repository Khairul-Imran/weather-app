package com.example.weather_app.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HourlyForecastData {
    
    private String dateAndTime;
    private int timeEpoch;

    private double temperature; // celsius
    private double wind; // kph
    private int humidity;
    private double feelsLike; // celsius

    private String weatherConditionText;
    private String weatherConditionIcon;

    private int chanceOfRain; // in %
    private int chanceOfSnow; // in %
}
