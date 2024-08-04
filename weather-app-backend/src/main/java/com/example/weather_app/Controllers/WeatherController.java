package com.example.weather_app.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.weather_app.Exceptions.WeatherServiceException;
import com.example.weather_app.Models.WeatherData;
import com.example.weather_app.Services.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    
    private final WeatherService weatherService;
    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{location}")
    public ResponseEntity<?> getWeatherForLocation(@PathVariable String location) {
        logger.info("Received request for weather data from location: {}", location);

        try {
            WeatherData weatherData = weatherService.getWeatherForLocation(location);
            logger.info("Successfully retrieved weather data for location: {}", location);
            return ResponseEntity.ok(weatherData);
        } catch (WeatherServiceException e) {
            logger.error("Error fetching weather data for location: {}", location, e);
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error fetching weather data: " + e.getMessage());
        }
    }
}