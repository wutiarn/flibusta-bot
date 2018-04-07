package com.wutiarn.flibustabot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

@Component
public class FlibustaBot extends TelegramLongPollingBot {

    private String token;
    private String username;

    public FlibustaBot(
            @Value("${flibustabot.telegram.token}") String token,
            @Value("${flibustabot.telegram.username}") String username,
            TelegramBotsApi botsApi
    ) throws TelegramApiRequestException {
        this.token = token;
        this.username = username;

        botsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
