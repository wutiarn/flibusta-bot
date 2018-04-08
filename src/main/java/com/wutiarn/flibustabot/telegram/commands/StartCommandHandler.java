package com.wutiarn.flibustabot.telegram.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StartCommandHandler implements TelegramCommandHandler {
    private static final Pattern messagePattern = Pattern.compile("/start");

    private final Logger logger = LoggerFactory.getLogger(StartCommandHandler.class);

    @Override
    public Pattern getMessagePattern() {
        return messagePattern;
    }

    @Override
    public BaseRequest<? extends BaseRequest, ? extends BaseResponse> handleMessage(Message message, Matcher commandMatcher) {
        return new SendMessage(message.chat().id(), "Привет! Я бот, созданный чтобы облегчить скачивание книг с " +
                "Флибусты. Просто отправь мне название книги, найди нужную из результатов поиска и выбери формат.");
    }
}
