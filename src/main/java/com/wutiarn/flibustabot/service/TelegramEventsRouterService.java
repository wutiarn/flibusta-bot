package com.wutiarn.flibustabot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TelegramEventsRouterService {
    Logger logger = LoggerFactory.getLogger(TelegramEventsRouterService.class);

    public BaseRequest<? extends BaseRequest, ? extends BaseResponse> processUpdate(Update update) {
        return null;
    }
}
