package com.tablet.notification.telegram.bot.handler;

import com.tablet.notification.telegram.bot.State;
import com.tablet.notification.telegram.bot.command.Command;
import com.tablet.notification.telegram.bot.keyboard.MainKeyboard;
import com.tablet.notification.telegram.bot.schedule.config.JobConfig;
import com.tablet.notification.telegram.model.Schedule;
import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.service.ScheduleService;
import com.tablet.notification.telegram.service.UserService;
import com.tablet.notification.telegram.util.BotUtil;
import com.tablet.notification.telegram.util.Html;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
//@Log4j
public class SaveScheduleHandler extends AbstractScheduleHandler {
    private UserService userService;

    @Autowired
    private JobConfig jobConfig;

    public SaveScheduleHandler(ScheduleService scheduleService, UserService userService) {
        super(scheduleService);
        this.userService = userService;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> operate(User user, Message message) {
        Schedule schedule = new Schedule(Integer.parseInt(user.getMetaInfo()), Integer.parseInt(message.getText()));
        scheduleService.addSchedule(schedule, user.getId());

        user.setState(State.START);
        user.setMetaInfo(null);
        userService.save(user);

        jobConfig.scheduler();
//        log.info("Пользователь [" + user.getName() + " (" + user.getId() + ")] вызвал [" + message.getText() + "]");
        return Collections.singletonList(createMessageTemplate(user)
                .text("Напоминание на " + Html.bold(schedule.getHour()) + " часов " + Html.bold(BotUtil.formatMinutes(schedule.getMinutes()) + " минут успешно создано"))
                .replyMarkup(new MainKeyboard().init().getReplyKeyboardMarkup())
                .build());
    }

    @Override
    public Command supportedCommand() {
        return Command.SAVE_SCHEDULE;
    }
}
