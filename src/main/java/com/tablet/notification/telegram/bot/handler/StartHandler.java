package com.tablet.notification.telegram.bot.handler;

import com.tablet.notification.telegram.bot.command.Command;
import com.tablet.notification.telegram.bot.keyboard.MainKeyboard;
import com.tablet.notification.telegram.model.User;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static com.tablet.notification.telegram.bot.command.Command.START;

@Component
@PropertySource("classpath:bot/bot.properties")
//@Log4j
public class StartHandler extends AbstractBaseHandler {

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> operate(User user, Message message) {
        StringBuilder helloText = new StringBuilder();
        helloText
                .append("Привет, ").append(user.getName()).append(EmojiParser.parseToUnicode(":wave:")).append("\n")
                .append("Я могу помочь тебе не забыть выпить таблетку.").append("\n")
                .append("Каждые 10 минут я буду уведомлять тебя о необходимости выпить таблетку").append("\n")
                .append("Ты можешь задавать несколько таймеров для разных таблеток. Плюс ко всему ты можешь задавать цикл употребления таблетки");
//        log.info("Пользователь [" + user.getName() + " (" + user.getId() + ")] вызвал [" + message.getText() + "]");
        return Collections.singletonList(createMessageTemplate(user)
                .text(helloText.toString())
                .replyMarkup(new MainKeyboard().init().getReplyKeyboardMarkup())
                .build());
    }

    @Override
    public Command supportedCommand() {
        return START;
    }
}
