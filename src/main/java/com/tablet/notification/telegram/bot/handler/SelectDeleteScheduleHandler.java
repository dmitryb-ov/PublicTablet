package com.tablet.notification.telegram.bot.handler;

import com.tablet.notification.telegram.bot.State;
import com.tablet.notification.telegram.bot.command.Command;
import com.tablet.notification.telegram.bot.keyboard.ChooseDeleteScheduleKeyboard;
import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.service.ScheduleService;
import com.tablet.notification.telegram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
//@Log4j
public class SelectDeleteScheduleHandler extends AbstractScheduleHandler {

    @Autowired
    private UserService userService;

    public SelectDeleteScheduleHandler(ScheduleService scheduleService) {
        super(scheduleService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> operate(User user, Message message) {
        user.setState(State.DELETE_SCHEDULE);
        userService.save(user);
//        log.info("Пользователь [" + user.getName() + " (" + user.getId() + ")] вызвал [" + message.getText() + "]");
        return Collections.singletonList(createMessageTemplate(user)
                .text("Выбери время")
                .replyMarkup(new ChooseDeleteScheduleKeyboard(user).init().getReplyKeyboardMarkup())
                .build());
    }

    @Override
    public Command supportedCommand() {
        return Command.SELECT_DELETE_SCHEDULE;
    }
}
