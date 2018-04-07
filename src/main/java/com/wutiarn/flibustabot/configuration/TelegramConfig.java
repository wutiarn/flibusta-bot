package com.wutiarn.flibustabot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramConfig {
    private String token;

    public TelegramConfig(@Value("${flibustabot.telegram.token}") String token) {
        this.token = token;
    }

    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(token);
    }
}
