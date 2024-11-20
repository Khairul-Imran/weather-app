package com.example.weather_app.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.weather_app.Config.WeatherApiConfig;
import com.example.weather_app.Exceptions.WeatherDataParsingException;
import com.example.weather_app.Exceptions.WeatherServiceException;
import com.example.weather_app.Models.WeatherData;
import com.example.weather_app.Utils.WeatherDataParser;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final WeatherApiConfig config;
    private final WeatherDataParser weatherDataParser;
    private final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    public WeatherService(RestTemplate restTemplate, WeatherApiConfig config, WeatherDataParser weatherDataParser) {
        this.restTemplate = restTemplate;
        this.config = config;
        this.weatherDataParser = weatherDataParser;
    }

    public WeatherData getWeatherForLocation(String location) throws WeatherServiceException {
        try {
            String url = buildUrl(location);
            logger.info("Making request to Weather API: {}", url);

            RequestEntity<Void> request = RequestEntity
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
            
            ResponseEntity<String> response = restTemplate.exchange(request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return weatherDataParser.parseWeatherData(response.getBody());
            } else {
                logger.error("Error response from WeatherAPI. Status: {}", response.getStatusCode());
                throw new WeatherServiceException("Failed to fetch weather data. Status: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException hcee) {
            // Instead of wrapping in WeatherServiceException, rethrow the original exception
            logger.error("Weather API client error: Status: {}, Response: {}", 
                hcee.getStatusCode(), hcee.getResponseBodyAsString());
            throw hcee;  // Important: Rethrow the original exception
        } catch (RestClientException e) {
            logger.error("Error fetching weather data for location: {}", location, e);
            throw new WeatherServiceException("Failed to fetch weather data", e);
            
        } catch (WeatherDataParsingException e) {
            logger.error("Error parsing weather data for location: {}", location, e);
            throw new WeatherServiceException("Failed to parse weather data", e);
        }
    }

    private String buildUrl(String location) {
        return UriComponentsBuilder
            .fromHttpUrl(config.getApiUrl())
            .queryParam("key", config.getApiKey())
            .queryParam("q", location)
            .queryParam("days", 3) // new for forecast data
            .toUriString();
    }
}
