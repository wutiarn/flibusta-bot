package com.wutiarn.flibustabot.bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class StartCommand extends BotCommand {

    private Logger logger = LoggerFactory.getLogger(StartCommand.class);

    public StartCommand() {
        super("start", "Start the bot");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage sendMessage = new SendMessage(chat.getId(), "Hello world");
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error("Failed to send message", e);
        }
    }
}
