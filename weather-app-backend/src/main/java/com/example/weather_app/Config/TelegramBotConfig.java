package com.example.weather_app.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.example.weather_app.Services.WeatherBotService;

@Configuration
public class TelegramBotConfig {
    
    @Bean
    public TelegramBotsApi telegramBotsApi(WeatherBotService weatherBotService) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(weatherBotService);

        return api;
    }
}
