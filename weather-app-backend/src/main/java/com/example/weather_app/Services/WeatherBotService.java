package com.example.weather_app.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.example.weather_app.Config.TelegramBotConfig;
import com.example.weather_app.Config.TelegramConfig;

@Component
public class WeatherBotService extends TelegramLongPollingBot {

    private final WeatherService weatherService;
    private final TelegramBotConfig telegramBotConfig;
    private final TelegramConfig telegramConfig;
    private final Logger logger = LoggerFactory.getLogger(WeatherBotService.class);

    @Autowired
    public WeatherBotService(WeatherService weatherService, TelegramBotConfig telegramBotConfig, TelegramConfig telegramConfig) {
        super(telegramConfig.getBotToken());
        this.weatherService = weatherService;
        this.telegramBotConfig = telegramBotConfig;
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

        if ("/start".equals(messageText)) { // First-time user interaction
            sendWelcomeMessage(chatId);
            sendLocationOptions(chatId);
        } else if ("/help".equals(messageText)) { // User requesting for help
            sendHelpMessage(chatId);
        }
    }

    // Weather data has been requested
    private void handleCallbackQuery(CallbackQuery callbackQuery) {

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
        keyboard.add(Arrays.asList(
            createLocationButton()
        ));

        markup.setKeyboard(keyboard);

        SendMessage message = SendMessage.builder()
            .chatId(String.valueOf(chatId))
            .text("Select a location: ")
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

    private InlineKeyboardButton createLocationButton() {
                
    }

}
