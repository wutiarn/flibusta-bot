package com.wutiarn.flibustabot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.List;

@Component
public class FlibustaBot extends TelegramLongPollingCommandBot {

    private String token;

    private Logger logger = LoggerFactory.getLogger(FlibustaBot.class);

    public FlibustaBot(
            @Value("${flibustabot.telegram.token}") String token,
            @Value("${flibustabot.telegram.username}") String username,
            TelegramBotsApi botsApi,
            List<BotCommand> commands
    ) throws TelegramApiRequestException {
        super(username);
        this.token = token;

        commands.forEach(this::register);

        botsApi.registerBot(this);
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
        if (message != null && message.getChat().isUserChat() && message.hasText()) {
            processPrivateTextMessage(message);
        }
    }

    @Override
    public String getBotToken() {
        return token;
    }

    private void processPrivateTextMessage(Message message) {
    }
}
