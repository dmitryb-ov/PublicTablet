package com.tablet.notification.telegram.bot;

import com.tablet.notification.telegram.bot.command.Command;
import com.tablet.notification.telegram.bot.handler.AbstractBaseHandler;
import com.tablet.notification.telegram.bot.keyboard.keyboard_button.Button;
import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.service.UserService;
import com.tablet.notification.telegram.util.ValidationUtil;
import com.tablet.notification.telegram.util.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static com.tablet.notification.telegram.bot.command.Command.*;

@Component
@Slf4j
@PropertySource("classpath:bot/bot.properties")
public class UpdateReceiver {
    private final UserService userService;
    private final List<AbstractBaseHandler> handlers;

    @Value("${bot.admin}")
    private String botAdmin;

    public UpdateReceiver(UserService userService, List<AbstractBaseHandler> handlers) {
        this.userService = userService;
        this.handlers = handlers;
    }

    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final Message message = update.getMessage();
            final User user = getUser(message);
            AbstractBaseHandler handler = handlers.stream()
                    .filter(h -> getCommand(user, message)
                            .equals(h.supportedCommand()))
                    .findAny()
                    .orElse(null);
            if (handler != null) {
                switch (handler.supportedCommand()) {
                    case ALL_MESSAGE:
                        return handler.multiplyOperate(userService.getUsers(), message);
                    default:
                        return handler.operate(user, message);
                }
            }
        }
        return Collections.emptyList();
    }

    private Command getCommand(User user, Message message) {
        String messageContent = message.getText();

        if (messageContent.startsWith("/")) {
            switch (messageContent.substring(1).split(" ")[0].toUpperCase()) {
                case "START":
                    return START;
                case "HELP":
                    return !user.isNew() ? HELP : UNAUTHORIZED;
                case "ALL":
                    return ALL_MESSAGE;
                case "ADDIMAGES":
                    return ADD_IMAGES;
                case "INFO":
                    if (user.getChatId() == Long.parseLong(botAdmin)) {
                        return INFO;
                    }
                case "GIF":
                    return GIF;
            }
        }
        if (messageContent.equals(Button.ADD_TIME.toString())) {
            return ADD_HOUR;
        } else if (messageContent.equals(Button.CANCEL.toString())) {
            return CANCEL;
        } else if (messageContent.equals(Button.SELECT_DELETE_TIME.toString())) {
            return SELECT_DELETE_SCHEDULE;
        } else if (messageContent.equals(Button.DRINK.toString())) {
            return DRINK;
        } else if (messageContent.contains("Напоминание")
                && messageContent.contains("(")
                && messageContent.contains(")") && user.getState() == State.DELETE_SCHEDULE) {
            return DELETE_CURRENT_SCHEDULE;
        } else if (messageContent.equals(Button.EDIT_TIME.toString())) {
            return SELECT_EDIT_SCHEDULE;
        } else if (messageContent.contains("Напоминание")
                && messageContent.contains("(")
                && messageContent.contains(")") && user.getState() == State.EDIT_SCHEDULE) {
            return EDIT_CURRENT_SCHEDULE;
        } else if (user.getState() == State.SELECT_EDIT_SCHEDULE) {
            return PROCESS_EDIT_SCHEDULE;
        }

        if (ValidationUtil.isNumeric(messageContent)) {
            if (user.getState() == State.SELECT_HOURS) {
                int hour = Integer.parseInt(messageContent);
                if (hour >= 0 && hour <= 24) {
                    return ADD_MINUTES;
                }
            } else if (user.getState() == State.SELECT_MINUTES) {
                int minute = Integer.parseInt(messageContent);
                if (minute >= 0 && minute <= 59) {
                    return SAVE_SCHEDULE;
                }
            }

        }

        return NONE;
    }


    private User getUser(Message message) {
        //This unboxing is require heroku!
        final int chatId = message.getFrom().getId().intValue();
        final String name = message.getFrom().getFirstName();
        try {
            final User user = userService.get((long) chatId);
            log.debug("Logged user: {}", user.toString());
            return user;
        } catch (NotFoundException e) {
            log.debug("User {} not found", chatId);
            final User user = userService.save(new User((long) chatId, name));
            log.debug("Saved new user to database: {}", user.toString());
            return user;
        }
    }
}
