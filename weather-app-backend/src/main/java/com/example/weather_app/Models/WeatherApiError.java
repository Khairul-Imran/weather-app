package com.example.weather_app.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherApiError {
    private Error error;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Error {
        private int code;
        private String message;
    }

    // Add a convenience constructor
    public WeatherApiError(int code, String message) {
        this.error = new Error(code, message);
    }
}
