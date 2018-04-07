package com.wutiarn.flibustabot.service;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import com.wutiarn.flibustabot.model.opds.BookSearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramEventsRouterService {
    Logger logger = LoggerFactory.getLogger(TelegramEventsRouterService.class);

    final FlibustaService flibustaService;

    @Autowired
    public TelegramEventsRouterService(FlibustaService flibustaService) {
        this.flibustaService = flibustaService;
    }

    public BaseRequest<? extends BaseRequest, ? extends BaseResponse> processUpdate(Update update) {
        Message message = update.message();
        if (message == null || message.chat().type() != Chat.Type.Private) {
            return null;
        }

        if (message.text() != null) {
            handleSearchRequest(message);
        }
        return null;
    }

    private void handleSearchRequest(Message message) {
        String query = message.text();
        BookSearchResult searchResults = flibustaService.search(query, FlibustaService.SearchType.BOOKS);
    }
}
