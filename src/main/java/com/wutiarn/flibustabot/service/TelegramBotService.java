package com.wutiarn.flibustabot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.GetMe;
import com.pengrad.telegrambot.response.GetMeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotService implements ApplicationRunner {

    private final TelegramBot bot;

    public TelegramBotService(@Value("${flibustabot.telegram.token}") String token) {
        bot = new TelegramBot(token);
    }

    @Override
    public void run(ApplicationArguments args) {
        testBotConfiguration();

        bot.setUpdatesListener(updates -> {
            updates.forEach(update -> {
                Message message = update.message();
                if (message != null) {
                    processMessage(message);
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void testBotConfiguration() {
        GetMeResponse resp = bot.execute(new GetMe());
        if (!resp.isOk()) {
            throw new IllegalStateException(String.format("Can't use getMe telegram method. " +
                    "Is bot token valid?. Error code: %d", resp.errorCode()));
        }
    }

    private void processMessage(Message message) {

    }
}
