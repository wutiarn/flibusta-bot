package com.wutiarn.flibustabot.exceptions.telegram;

import com.pengrad.telegrambot.response.BaseResponse;

public class GettingUpdatesFailedException extends RuntimeException {
    public GettingUpdatesFailedException(BaseResponse response) {
        super(String.format("Status %d: %s", response.errorCode(), response.description()));
    }
}
