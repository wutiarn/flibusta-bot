package com.wutiarn.flibustabot.service;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.wutiarn.flibustabot.model.opds.BookSearchResult;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;

@Service
public class TelegramEventsRouterService {
    Logger logger = LoggerFactory.getLogger(TelegramEventsRouterService.class);

    final FlibustaService flibustaService;

    @Autowired
    Configuration freemarkerConfiguration;

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
            return handleSearchRequest(message);
        }
        return null;
    }

    private BaseRequest<? extends BaseRequest, ? extends BaseResponse> handleSearchRequest(Message message) {
        String query = message.text();
        BookSearchResult searchResults = flibustaService.search(query, FlibustaService.SearchType.BOOKS);

        var data = new HashMap<String, Object>();
        data.put("results", searchResults);

        String renderedResult;

        try {
            Template template = freemarkerConfiguration.getTemplate("searchResults.ftl");

            ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(resultStream);
            template.process(data, writer);

            writer.close();
            renderedResult = resultStream.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new SendMessage(message.chat().id(), renderedResult).parseMode(ParseMode.HTML);
    }
}
