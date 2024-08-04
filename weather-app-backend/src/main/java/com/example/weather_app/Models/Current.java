package com.example.weather_app.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Current {
    private String lastUpdate;
    private double temperature; // celsius
    private double wind; // kph
    private int humidity;
    private double feelsLike; // celsius
    // Condition
    private String description;
    private String icon;
}