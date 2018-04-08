package com.wutiarn.flibustabot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.wutiarn.flibustabot.exceptions.telegram.GettingUpdatesFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TelegramPollingService implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(TelegramPollingService.class);

    private TelegramBot bot;
    final TelegramEventsRouterService routerService;

    @Autowired
    public TelegramPollingService(TelegramBot bot, TelegramEventsRouterService routerService) {
        this.bot = bot;
        this.routerService = routerService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(() -> {
            //noinspection InfiniteLoopStatement
            while (true) {
                try {
                    poll();
                } catch (Exception e) {
                    logger.error("Exception occurred while polling telegram updates", e);
                    try {
                        Thread.sleep(10_000);
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }
                }
            }
        }, "TgPollingThread").start();
    }

    private void poll() {
        logger.info("Starting telegram polling service");

        AtomicInteger lastUpdateId = new AtomicInteger();
        //noinspection InfiniteLoopStatement
        while (true) {
            GetUpdates getUpdatesRequest = new GetUpdates().offset(lastUpdateId.get() + 1).timeout(30);
            GetUpdatesResponse response = bot.execute(getUpdatesRequest);

            if (!response.isOk()) {
                throw new GettingUpdatesFailedException(response);
            }

            List<Update> updates = response.updates();
            if (updates == null) {
                continue;
            }

            updates.forEach(update -> {
                lastUpdateId.set(update.updateId());
                var resultRequest = routerService.processUpdate(update);
                if (resultRequest != null) {
                    BaseResponse resultResponse = bot.execute(resultRequest);
                    if (!resultResponse.isOk()) {
                        logger.info(String.format("Error sending response. Status %s: %s",
                                resultResponse.errorCode(), resultResponse.description()));
                    }
                }
            });
        }
    }
}
