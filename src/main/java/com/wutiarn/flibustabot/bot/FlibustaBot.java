package com.wutiarn.flibustabot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

@Component
public class FlibustaBot extends TelegramLongPollingBot {

    private String token;
    private String username;

    private Logger logger = LoggerFactory.getLogger(FlibustaBot.class);

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
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.getChat().isUserChat() && message.hasText()) {
            processPrivateTextMessage(message);
        }
    }

    private void processPrivateTextMessage(Message message) {
    }
}
