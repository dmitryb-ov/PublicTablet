package com.tablet.notification.telegram.bot.handler;

import com.tablet.notification.telegram.bot.State;
import com.tablet.notification.telegram.bot.command.Command;
import com.tablet.notification.telegram.bot.keyboard.MainKeyboard;
import com.tablet.notification.telegram.bot.schedule.config.JobConfig;
import com.tablet.notification.telegram.model.Schedule;
import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.service.ScheduleService;
import com.tablet.notification.telegram.service.UserService;
import com.tablet.notification.telegram.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
//@Log4j
public class DeleteCurrentScheduleHandler extends AbstractScheduleHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private JobConfig jobConfig;

    public DeleteCurrentScheduleHandler(ScheduleService scheduleService) {
        super(scheduleService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> operate(User user, Message message) {
        Matcher matcher = Pattern.compile("((\\d){6})").matcher(message.getText());
        user.setState(State.START);
        userService.save(user);
//        log.info("Пользователь [" + user.getName() + " (" + user.getId() + ")] вызвал [" + message.getText() + "]");

        if (matcher.find()) {
            if (ValidationUtil.isNumeric(matcher.group())) {
                int scheduleId = Integer.parseInt(matcher.group());
                Schedule schedule = scheduleService.get(scheduleId, user.getId());
                if (schedule != null) {
                    scheduleService.deleteSchedule(schedule);

                    jobConfig.scheduler();
                } else {
                    return Collections.singletonList(createMessageTemplate(user)
                            .text("Нет такого времени")
                            .replyMarkup(new MainKeyboard().init().getReplyKeyboardMarkup())
                            .build());
                }
            } else {
                return Collections.singletonList(createMessageTemplate(user)
                        .text("Что-то пошло не так, попробуйте позже")
                        .replyMarkup(new MainKeyboard().init().getReplyKeyboardMarkup())
                        .build());
            }
        }
        return Collections.singletonList(createMessageTemplate(user)
                .text("Удалено")
                .replyMarkup(new MainKeyboard().init().getReplyKeyboardMarkup())
                .build());
    }

    @Override
    public Command supportedCommand() {
        return Command.DELETE_CURRENT_SCHEDULE;
    }
}
