package com.wutiarn.flibustabot.service;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.wutiarn.flibustabot.model.opds.BookSearchResult;
import com.wutiarn.flibustabot.telegram.commands.TelegramCommandHandler;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

@Service
public class TelegramEventsRouterService {
    private Logger logger = LoggerFactory.getLogger(TelegramEventsRouterService.class);

    final FlibustaService flibustaService;

    private Configuration freemarkerConfiguration;

    private List<TelegramCommandHandler> commandHandlers;

    @Autowired
    public TelegramEventsRouterService(FlibustaService flibustaService, Configuration freemarkerConfiguration, List<TelegramCommandHandler> commandHandlers) {
        this.flibustaService = flibustaService;
        this.freemarkerConfiguration = freemarkerConfiguration;
        this.commandHandlers = commandHandlers;
    }

    public BaseRequest<? extends BaseRequest, ? extends BaseResponse> processUpdate(Update update) {
        Message message = update.message();
        if (message == null || message.chat().type() != Chat.Type.Private) {
            return null;
        }

        String messageText = message.text();
        logger.info(String.format("Received from %s (%s): %s", message.chat().username(), message.chat().id(), messageText));
        if (messageText != null) {
            if (messageText.startsWith("/")) {
                for (var handler : commandHandlers) {
                    Matcher matcher = handler.getMessagePattern().matcher(messageText);
                    if (matcher.matches()) {
                        return handler.handleMessage(message, matcher);
                    }
                }
                return new SendMessage(message.chat().id(), "Unsupported command");
            } else {
                return handleSearchRequest(message);
            }
        }
        return null;
    }

    private BaseRequest<? extends BaseRequest, ? extends BaseResponse> handleSearchRequest(Message message) {
        String query = message.text();
        BookSearchResult searchResults = flibustaService.searchBooks(query);

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
