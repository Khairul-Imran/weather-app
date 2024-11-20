package com.example.weather_app.Utils;

import java.io.StringReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.weather_app.Exceptions.WeatherDataParsingException;
import com.example.weather_app.Models.Current;
import com.example.weather_app.Models.DailyAstroData;
import com.example.weather_app.Models.DailyForecastData;
import com.example.weather_app.Models.Forecast;
import com.example.weather_app.Models.HourlyForecastData;
import com.example.weather_app.Models.Location;
import com.example.weather_app.Models.WeatherData;

import jakarta.json.Json;
import jakarta.json.JsonArray;
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

            // Forecast data
            Forecast forecastObject = parseForecast(jsonObject);

            Location locationObject = new Location(name, region, country);
            Current currentObject = new Current(lastUpdate, temperature, wind, humidity, feelsLike, description, icon);

            return new WeatherData(locationObject, currentObject, forecastObject);
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

    // Forecast
    private Forecast parseForecast(JsonObject jsonObject) {
        // 3 days -> Json Objects
        JsonArray forecastDays = jsonObject.getJsonObject("forecast").getJsonArray("forecastday");
        
        // Get the first forecast day to extract the date
        JsonObject firstDay = forecastDays.getJsonObject(0);
        String date = firstDay.getString("date");
        int dateEpoch = firstDay.getInt("date_epoch");

        // Initialise arrays
        DailyForecastData[] dailyForecastData = new DailyForecastData[forecastDays.size()];
        DailyAstroData[] dailyAstroData = new DailyAstroData[forecastDays.size()];
        HourlyForecastData[][] hourlyForecastData = new HourlyForecastData[forecastDays.size()][];
        
        // Parse daily forecast data
        for (int i = 0; i < forecastDays.size(); i++) {
            // Iterating through the forecastDays array -> which contains the data for each day
            JsonObject forecastDay = forecastDays.getJsonObject(i);
            
            // Parse daily forecast data
            JsonObject day = forecastDay.getJsonObject("day");
            dailyForecastData[i] = new DailyForecastData(
                day.getJsonNumber("maxtemp_c").doubleValue(),
                day.getJsonNumber("mintemp_c").doubleValue(),
                day.getJsonNumber("avgtemp_c").doubleValue(),
                day.getJsonNumber("maxwind_kph").doubleValue(),
                day.getJsonNumber("totalprecip_mm").doubleValue(),
                day.getJsonNumber("totalsnow_cm").doubleValue(),
                day.getInt("avghumidity"),
                day.getJsonObject("condition").getString("text"),
                day.getJsonObject("condition").getString("icon"),
                day.getInt("daily_chance_of_rain"),
                day.getInt("daily_chance_of_snow")
            );

            // Parse daily astro data
            JsonObject astro = forecastDay.getJsonObject("astro");
            dailyAstroData[i] = new DailyAstroData(
                astro.getString("sunrise"),
                astro.getString("sunset"),
                astro.getString("moonrise"),
                astro.getString("moonset")
            );

            // Parse hourly data for the day
            // Iterate through the hour array -> contains json objects for each hour
            JsonArray hours = forecastDay.getJsonArray("hour");
            hourlyForecastData[i] = new HourlyForecastData[hours.size()]; // Initialising the size of the nested array - which represents hourly data for each day
            
            // Iterate through the nested array
            for (int j = 0; j < hours.size(); j++) {
                JsonObject hour = hours.getJsonObject(j);
                hourlyForecastData[i][j] = new HourlyForecastData(
                    hour.getString("time"),
                    hour.getInt("time_epoch"),
                    hour.getJsonNumber("temp_c").doubleValue(),
                    hour.getJsonNumber("wind_kph").doubleValue(),
                    hour.getInt("humidity"),
                    hour.getJsonNumber("feelslike_c").doubleValue(),
                    hour.getJsonObject("condition").getString("text"),
                    hour.getJsonObject("condition").getString("icon"),
                    hour.getInt("chance_of_rain"),
                    hour.getInt("chance_of_snow")
                );
            }
        }

        return new Forecast(date, dateEpoch, dailyForecastData, dailyAstroData, hourlyForecastData);
    }

}
