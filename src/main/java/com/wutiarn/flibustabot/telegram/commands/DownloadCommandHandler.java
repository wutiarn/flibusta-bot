package com.wutiarn.flibustabot.telegram.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import com.wutiarn.flibustabot.service.FlibustaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DownloadCommandHandler implements TelegramCommandHandler {
    private static final Pattern messagePattern = Pattern.compile("/dl_(?<format>\\w+)_(?<id>\\d+)");

    private final FlibustaService flibustaService;
    private final TelegramBot bot;

    private Executor executor = Executors.newCachedThreadPool();

    @Autowired
    public DownloadCommandHandler(FlibustaService flibustaService, TelegramBot bot) {
        this.flibustaService = flibustaService;
        this.bot = bot;
    }

    @Override
    public Pattern getMessagePattern() {
        return messagePattern;
    }

    @Override
    public BaseRequest<? extends BaseRequest, ? extends BaseResponse> handleMessage(Message message, Matcher commandMatcher) {
        String bookFormat = commandMatcher.group("format");
        String bookId = commandMatcher.group("id");
        SendResponse sendResult = bot.execute(
                new SendMessage(message.chat().id(), "Подготовка").replyToMessageId(message.messageId()));

        int statusMessageId = sendResult.message().messageId();

        executor.execute(() -> {
            downloadAndSendBook(message.chat().id(), statusMessageId, bookId, bookFormat);
        });

        return null;
    }

    private void downloadAndSendBook(long chatId, int statusMessageId, String bootId, String bookFormat) {
        bot.execute(new EditMessageText(chatId, statusMessageId, "Загрузка"));
    }
}
