package com.tablet.notification.telegram.bot.handler;

import com.tablet.notification.telegram.bot.command.Command;
import com.tablet.notification.telegram.model.User;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Slf4j
public abstract class AbstractBaseHandler {

    protected static final String END_LINE = "\n";
    protected static final String EMPTY_LINE = "---------------------------";
    protected static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public abstract List<PartialBotApiMethod<? extends Serializable>> operate(User user, Message message);

    public List<PartialBotApiMethod<? extends Serializable>> multiplyOperate(List<User> users, Message message) {
        return Collections.emptyList();
    }

    /*
     * Creates SendMessage template with markdown enabled for user with chatId
     */
    protected final SendMessage.SendMessageBuilder createMessageTemplate(User user) {
        log.info("Called createMessageTemplate. UserId: {}", user.getId());
        return SendMessage.builder()
                .parseMode(ParseMode.HTML)
                .chatId(String.valueOf(user.getChatId()));
    }

    protected final SendPhoto.SendPhotoBuilder createPhotoMessageTemplate(User user) {
        log.info("Called createPhotoMessageTemplate. UserId: {}", user.getId());
        return SendPhoto.builder()
                .parseMode(ParseMode.HTML)
                .chatId(String.valueOf(user.getChatId()));
    }

    protected final SendAnimation.SendAnimationBuilder createAnimationMessageTemplate(User user) {
        log.info("Called createAnimationMessageTemplate. UserId: {}", user.getId());
        return SendAnimation.builder()
                .parseMode(ParseMode.HTML)
                .chatId(String.valueOf(user.getChatId()));
    }

    protected final SendDocument.SendDocumentBuilder createDocumentMessageTemplate(User user) {
        log.info("Called createDocumentMessageTemplate. UserId: {}", user.getId());
        return SendDocument.builder()
                .parseMode(ParseMode.HTML)
                .chatId(String.valueOf(user.getChatId()));
    }

    public abstract Command supportedCommand();
}
