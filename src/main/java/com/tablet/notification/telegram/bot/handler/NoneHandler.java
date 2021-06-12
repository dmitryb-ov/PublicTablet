package com.tablet.notification.telegram.bot.handler;

import com.tablet.notification.telegram.bot.command.Command;
import com.tablet.notification.telegram.model.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
//@Log4j
public class NoneHandler extends AbstractBaseHandler {
    @Override
    public List<PartialBotApiMethod<? extends Serializable>> operate(User user, Message message) {
//        log.info("Пользователь [" + user.getName() + " (" + user.getId() + ")] вызвал [" + message.getText() + "]");
        return Collections.singletonList(createMessageTemplate(user)
                .text("Не знаю таких слов/команд.\n/help для помощи")
                .build());
    }

    @Override
    public Command supportedCommand() {
        return Command.NONE;
    }
}
