package com.wutiarn.flibustabot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TelegramBotService implements ApplicationRunner {

    private final TelegramBot bot;

    public TelegramBotService(@Value("${flibustabot.telegram.token}") String token) {
        bot = new TelegramBot(token);
    }

    @Override
    public void run(ApplicationArguments args) {
        new Thread(this::receiveUpdates).start();
    }

    private void receiveUpdates() {
        AtomicInteger lastOffset = new AtomicInteger();
        //noinspection InfiniteLoopStatement
        while (true) {
            GetUpdatesResponse response = bot.execute(new GetUpdates().timeout(60).offset(lastOffset.get()));
            List<Update> updates = response.updates();
            if (updates == null) {
                continue;
            }

            updates.forEach((update) -> {
                lastOffset.set(update.updateId() + 1);
                Message message = update.message();

                if (message != null) {
                    processMessage(message);
                }
            });
        }
    }

    private void processMessage(Message message) {

    }
}
