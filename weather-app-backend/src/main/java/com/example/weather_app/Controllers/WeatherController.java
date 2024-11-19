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
import org.springframework.web.client.HttpClientErrorException;

import com.example.weather_app.Models.WeatherApiError;
import com.example.weather_app.Models.WeatherData;
import com.example.weather_app.Services.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        } catch (HttpClientErrorException e) {
            // logger.error("Error fetching weather data for location: {}", location, e);
            logger.error("Error fetching weather data for location: {}. Status: {}. Response: {}", location, e.getStatusCode(), e.getResponseBodyAsString());

            try {
                // Parse the error from the weather API
                WeatherApiError weatherApiError = new ObjectMapper().readValue(e.getResponseBodyAsString(), WeatherApiError.class);

                // Important: Use the original status code from the weatherAPI (400)
                return ResponseEntity
                    .status(e.getStatusCode())  // This will be 400 for invalid locations
                    .body(weatherApiError);     // This contains the code 1006 and message

                // return ResponseEntity.status(e.getStatusCode()).body(weatherApiError);
            } catch (JsonProcessingException jpe) {

                logger.error("Failed to parse weather API error response", jpe);
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)  // Still use 400 as it's a client error
                    .body(new WeatherApiError(1006, "Invalid location or parsing error"));

                // // If we can't parse the weather Api error, return a generic error
                // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new WeatherApiError(500, "An unexpected error occurred"));
            }

        } catch (Exception e) {

            logger.error("Unexpected error fetching weather data", e);
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new WeatherApiError(500, "An unexpected server error occurred"));

            // This was the original error that we were seeing
            // return ResponseEntity
            //     .status(HttpStatus.INTERNAL_SERVER_ERROR)
            //     .body("Error fetching weather data: " + e.getMessage());
        }
    }
}
