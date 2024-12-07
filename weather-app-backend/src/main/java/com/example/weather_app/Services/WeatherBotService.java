package com.example.weather_app.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

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

    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        
    }

}
