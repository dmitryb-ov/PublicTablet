package com.tablet.notification.telegram.bot.handler;

import com.tablet.notification.telegram.bot.command.Command;
import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.service.UserService;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractCommandHandler extends AbstractBaseHandler {
    protected final UserService userService;

    public AbstractCommandHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> operate(User user, Message message) {
        return null;
    }

    @Override
    public Command supportedCommand() {
        return null;
    }
}
