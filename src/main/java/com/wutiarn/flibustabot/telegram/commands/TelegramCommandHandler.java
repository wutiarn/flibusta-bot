package com.wutiarn.flibustabot.telegram.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface TelegramCommandHandler {
    Pattern getMessagePattern();
    BaseRequest<? extends BaseRequest, ? extends BaseResponse> handleMessage(Message message, Matcher commandMatcher);
}
