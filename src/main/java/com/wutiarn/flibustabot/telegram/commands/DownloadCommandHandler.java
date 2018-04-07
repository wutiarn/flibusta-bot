package com.wutiarn.flibustabot.telegram.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DownloadCommandHandler implements TelegramCommandHandler {
    private static final Pattern messagePattern = Pattern.compile("/dl_(?<format>\\w+)_(?<id>\\d+)");

    @Override
    public Pattern getMessagePattern() {
        return messagePattern;
    }

    @Override
    public BaseRequest<? extends BaseRequest, ? extends BaseResponse> handleMessage(Message message, Matcher commandMatcher) {
        return null;
    }
}
