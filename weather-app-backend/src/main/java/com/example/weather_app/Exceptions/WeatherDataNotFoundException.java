package com.example.weather_app.Exceptions;

// For now, we are not using this exception.
public class WeatherDataNotFoundException extends RuntimeException {

    private final String location;

    public WeatherDataNotFoundException(String location) {
        super("Weather data not found for location: " + location);
        this.location = location;
    }

    public WeatherDataNotFoundException(String location, Throwable cause) {
        super("Weather data not found for location: " + location, cause);
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
