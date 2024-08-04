package com.example.weather_app.Utils;

import java.io.StringReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.weather_app.Exceptions.WeatherDataParsingException;
import com.example.weather_app.Models.Current;
import com.example.weather_app.Models.Location;
import com.example.weather_app.Models.WeatherData;

import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Component
public class WeatherDataParser {
    private static final Logger logger = LoggerFactory.getLogger(WeatherDataParser.class);

    public WeatherData parseWeatherData(String jsonPayload) throws WeatherDataParsingException {
        try (JsonReader jsonReader = Json.createReader(new StringReader(jsonPayload))) {
            JsonObject jsonObject = jsonReader.readObject();

            String name = parseName(jsonObject);
            String region = parseRegion(jsonObject);
            String country = parseCountry(jsonObject);
            String lastUpdate = parseLastUpdate(jsonObject);
            double temperature = parseTemperature(jsonObject);
            double wind = parseWind(jsonObject);
            int humidity = parseHumidity(jsonObject);
            double feelsLike = parseFeelsLike(jsonObject);
            String description = parseDescription(jsonObject);
            String icon = parseIcon(jsonObject);

            Location locationObject = new Location(name, region, country);
            Current currentObject = new Current(lastUpdate, temperature, wind, humidity, feelsLike, description, icon);

            return new WeatherData(locationObject, currentObject);
        } catch (JsonException e) {
            logger.error("Error parsing JSON data", e);
            throw new WeatherDataParsingException("Error parsing JSON data", e);

        } catch (Exception e) {
            logger.error("Unexpected error while parsing weather data", e);
            throw new WeatherDataParsingException("Unexpected error while parsing weather data", e);
        }
    }


    private String parseName(JsonObject jsonObject) {
        return jsonObject.getJsonObject("location").getString("name", "");
    }

    private String parseRegion(JsonObject jsonObject) {
        return jsonObject.getJsonObject("location").getString("region", "");
    }

    private String parseCountry(JsonObject jsonObject) {
        return jsonObject.getJsonObject("location").getString("country", "");
    }

    private String parseLastUpdate(JsonObject jsonObject) {
        return jsonObject.getJsonObject("current").getString("last_updated", "");
    }

    private double parseTemperature(JsonObject jsonObject) {
        return jsonObject.getJsonObject("current").getJsonNumber("temp_c").doubleValue();
    }

    private double parseWind(JsonObject jsonObject) {
        return jsonObject.getJsonObject("current").getJsonNumber("wind_kph").doubleValue();
    }

    private int parseHumidity(JsonObject jsonObject) {
        return jsonObject.getJsonObject("current").getJsonNumber("humidity").intValue();
    }

    private double parseFeelsLike(JsonObject jsonObject) {
        return jsonObject.getJsonObject("current").getJsonNumber("feelslike_c").doubleValue();
    }

    private String parseDescription(JsonObject jsonObject) {
        return jsonObject.getJsonObject("current").getJsonObject("condition").getString("text", "");
    }

    private String parseIcon(JsonObject jsonObject) {
        return jsonObject.getJsonObject("current").getJsonObject("condition").getString("icon", "");
    }
}
