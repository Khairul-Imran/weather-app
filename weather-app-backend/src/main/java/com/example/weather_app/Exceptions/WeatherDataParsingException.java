package com.example.weather_app.Exceptions;

public class WeatherDataParsingException extends Exception {
    public WeatherDataParsingException(String message) {
        super(message);
    }

    public WeatherDataParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeatherDataParsingException(Throwable cause) {
        super(cause);
    }

    public WeatherDataParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}