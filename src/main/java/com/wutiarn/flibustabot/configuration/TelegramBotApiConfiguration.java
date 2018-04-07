package com.wutiarn.flibustabot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

@Configuration
public class TelegramBotApiConfiguration {
    @Bean
    public TelegramBotsApi telegramBotsApi() {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        return botsApi;
    }
}
