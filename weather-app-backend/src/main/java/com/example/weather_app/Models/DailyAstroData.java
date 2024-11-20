package com.example.weather_app.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyAstroData {

    // Times in string
    private String sunriseTime;
    private String sunsetTime;
    private String moonriseTime;
    private String moonsetTime;

}
