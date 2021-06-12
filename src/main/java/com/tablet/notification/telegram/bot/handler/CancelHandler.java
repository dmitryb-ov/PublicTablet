package com.tablet.notification.telegram.bot.handler;

import com.tablet.notification.telegram.bot.State;
import com.tablet.notification.telegram.bot.command.Command;
import com.tablet.notification.telegram.bot.keyboard.MainKeyboard;
import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
//@Log4j
public class CancelHandler extends AbstractCommandHandler {

    public CancelHandler(UserService userService) {
        super(userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> operate(User user, Message message) {
        user.setState(State.START);
        user.setMetaInfo(null);
        userService.save(user);
//        log.info("Пользователь [" + user.getName() + " (" + user.getId() + ")] вызвал [" + message.getText() + "]");
        return Collections.singletonList(createMessageTemplate(user)
                .text("Действие отменено")
                .replyMarkup(new MainKeyboard().init().getReplyKeyboardMarkup())
                .build());
    }

    @Override
    public Command supportedCommand() {
        return Command.CANCEL;
    }
}
