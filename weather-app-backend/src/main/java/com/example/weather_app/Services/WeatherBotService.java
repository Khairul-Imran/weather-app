package com.example.weather_app.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.example.weather_app.Config.TelegramConfig;
import com.example.weather_app.Models.WeatherData;

@Component
public class WeatherBotService extends TelegramLongPollingBot {

    private final WeatherService weatherService;
    private final WeatherMessageFormatter weatherMessageFormatter;
    private final TelegramConfig telegramConfig;
    private final Logger logger = LoggerFactory.getLogger(WeatherBotService.class);

    // Constanst for bot commands
    private static final String START_COMMAND = "/start";
    private static final String HELP_COMMAND = "/help";
    private static final String SEARCH_COMMAND = "/search";
    // Track users awaiting location input
    private final Set<Long> usersAwaitingLocation = new HashSet<>();

    // List of popular cities
    private static final List<String> POPULAR_CITIES = Arrays.asList(
        "London", "Manchester", "New York", "Tokyo", "Singapore", "Paris", 
        "Sydney", "Dubai", "Mumbai", "Toronto", "Las Vegas", "Shanghai"
    );

    @Autowired
    public WeatherBotService(WeatherService weatherService, WeatherMessageFormatter weatherMessageFormatter, TelegramConfig telegramConfig) {
        super(telegramConfig.getBotToken());
        this.weatherService = weatherService;
        this.weatherMessageFormatter = weatherMessageFormatter;
        this.telegramConfig = telegramConfig;
    }

    @Override
    public String getBotUsername() {
        return telegramConfig.getBotUsername();
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                handleTextMessage(update);
            } else if (update.hasCallbackQuery()) {
                handleCallbackQuery(update.getCallbackQuery());
            }
        } catch (Exception e) {
            logger.error("WeatherBotService - Error processing update: ", e);
        }
    }

    private void handleTextMessage(Update update) {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        switch (messageText) {
            case START_COMMAND:
                sendWelcomeMessage(chatId);
                sendLocationOptions(chatId);
                break;
            case HELP_COMMAND:
                sendHelpMessage(chatId);
                break;
            case SEARCH_COMMAND:
                promptForLocationSearch(chatId);
                break;
            default:
                // Checking if the message received from user is a response to a search prompt (promptForLocationSearch)
                if (isAwaitingLocationInput(chatId)) {
                    fetchAndSendWeatherData(chatId, messageText);
                    clearLocationInputState(chatId);
                }
        }
    }

    // Weather data has been requested
    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();
        String messageId = callbackQuery.getId();

        if ("SEARCH".equals(callbackData)) {
            promptForLocationSearch(chatId);
        } else if (callbackData.startsWith("LOCATION:")) {
            String location = callbackData.substring("LOCATION:".length());
            fetchAndSendWeatherData(chatId, location);
        } else if (callbackData.startsWith("HOURLY:")) {
            String location = callbackData.substring("HOURLY:".length());
            sendHourlyForecast(chatId,location);
        }

        try {
            execute(AnswerCallbackQuery.builder()
                .callbackQueryId(messageId)
                .text("Processing your request...") 
                .build()
            );
        } catch (TelegramApiException tae) {
            logger.error("WeatherBotService - Error acknowledging callback: ", tae);
        }
        // Acknowledge the callback query
        // try {
        //     execute(AnswerCallbackQuery.builder()
        //         .callbackQueryId(messageId)
        //         .text("Fetching weather data for " + location + "...")
        //         .build());
        // } catch (TelegramApiException tae) {
        //     logger.error("WeatherBotService - Error acknoledging callback query: " , tae);
        // }

        // Fetch and send the data
        // fetchAndSendWeatherData(chatId, location);
    }

    private void sendWelcomeMessage(long chatId) {
        String welcomeText = """
        
        Insert appropriate text here

        """;

        sendMessage(chatId, welcomeText);
    }

    private void sendHelpMessage(long chatId) {
        String helpText = """
        
        Insert appropriate text here

        """;

        sendMessage(chatId, helpText);
    }

    private void sendLocationOptions(long chatId) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        // Add buttons for common/popular locations
        // keyboard.add(Arrays.asList(
        //     createLocationButton("London")
        //     // Perhaps add more common locations here
        // ));

        // Creates rows of 3 buttons each for popular cities
        for (int i = 0; i < POPULAR_CITIES.size(); i += 3) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int j = i; j < Math.min(i + 3, POPULAR_CITIES.size()); j++) {
                row.add(createLocationButton(POPULAR_CITIES.get(j)));
            }
            keyboard.add(row);
        }

        // Add a search button at the bottom
        List<InlineKeyboardButton> searchRow = new ArrayList<>();
        searchRow.add(InlineKeyboardButton.builder()
            .text("🔍 Search Other Location")
            .callbackData("SEARCH")
            .build());
        keyboard.add(searchRow);

        markup.setKeyboard(keyboard);

        SendMessage message = SendMessage.builder()
            .chatId(String.valueOf(chatId))
            .text("Select a location or search for a specific city: ")
            .replyMarkup(markup)
            .build();

        try {
            execute(message);
        } catch (TelegramApiException tae) {
            logger.error("WeatherBotService - Error sending location options: ", tae);
        }
    }

    private void sendMessage(long chatId, String text) {
        try {
            execute(SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(text)
                .build());
        } catch (TelegramApiException tae) {
            logger.error("WeatherBotService - Error sending message: ", tae);
        }
    }

    private void fetchAndSendWeatherData(long chatId, String location) {
        try {
            // Fetch weather data
            WeatherData weatherData = weatherService.getWeatherForLocation(location);
            
            // Format and send current weather
            String currentWeatherMessage = weatherMessageFormatter.formatCurrentWeather(weatherData);
            sendMessage(chatId, currentWeatherMessage);

            // Format and sned daily forecast (3 days)
            String forecastMessage = weatherMessageFormatter.formatDailyForecast(weatherData);
            sendMessage(chatId, forecastMessage);

            // Add option to view hourly forecast
            sendHourlyForecastOption(chatId, location);

        } catch (Exception e) {
            String errorMessage = "Sorry, I couldn't find weather data for " + location + 
                ". Please check the spelling or try another location.";
            
            sendMessage(chatId, errorMessage);
            logger.error("Error fetching weather data: ", e);
        }
    }

    private void sendHourlyForecastOption(long chatId, String location) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        keyboard.add(Arrays.asList(
            InlineKeyboardButton.builder()
                .text("View Hourly Forecast")
                .callbackData("HOURLY: " + location)
                .build()
        ));

        markup.setKeyboard(keyboard);

        SendMessage message = SendMessage.builder()
            .chatId(String.valueOf(chatId))
            .text("Would you like to see the hourly forecast?")
            .replyMarkup(markup)
            .build();

        try {
            execute(message);
        } catch (TelegramApiException tae) {
            logger.error("Error sending hourly forecast option: ", tae);
        }
    }

    private void sendHourlyForecast(long chatId, String location) {
        try {
            WeatherData weatherData = weatherService.getWeatherForLocation(location);
            String hourlyForecast = weatherMessageFormatter.formatHourlyForecast(weatherData);

            // Splitting the message if it is too long
            if (hourlyForecast.length() > 4096) {
                List<String> parts = splitMessage(hourlyForecast);
                for (String part : parts) {
                    sendMessage(chatId, part);
                }
            } else {
                sendMessage(chatId, hourlyForecast);
            }
        } catch (Exception e) {
            sendMessage(chatId, "Sorry, I couldn't fetch the hourly forecast right now.");
            logger.error("WeatherBotService - Error sending hourly forecast: ", e);
        }
    }

    // Helper method to split long messages
    private List<String> splitMessage(String message) {
        List<String> parts = new ArrayList<>();
        int maxLength = 4000; // Slightly less than 4096 to be safe

        for (int i = 0; i < message.length(); i += maxLength) {
            int end = Math.min(i + maxLength, message.length());
            parts.add(message.substring(i, end));
        }

        return parts;
    }

    private InlineKeyboardButton createLocationButton(String label) {
        return InlineKeyboardButton.builder()
            .text(label)
            .callbackData("LOCATION: " + label)
            .build();
    }

    // Methods for managing location input state
    private void promptForLocationSearch(long chatId) {
        usersAwaitingLocation.add(chatId);
        sendMessage(chatId, "Please enter the name of the city you want to check: ");
    }

    private boolean isAwaitingLocationInput(long chatId) {
        return usersAwaitingLocation.contains(chatId);
    }

    private void clearLocationInputState(long chatId) {
        usersAwaitingLocation.remove(chatId);
    }
}
