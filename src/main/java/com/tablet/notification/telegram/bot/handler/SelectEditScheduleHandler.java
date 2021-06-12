package com.tablet.notification.telegram.bot.handler;

import com.tablet.notification.telegram.bot.State;
import com.tablet.notification.telegram.bot.command.Command;
import com.tablet.notification.telegram.bot.keyboard.ChooseEditScheduleKeyboard;
import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.service.ScheduleService;
import com.tablet.notification.telegram.service.UserService;
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
public class SelectEditScheduleHandler extends AbstractScheduleHandler {

    @Autowired
    private UserService userService;

    public SelectEditScheduleHandler(ScheduleService scheduleService) {
        super(scheduleService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> operate(User user, Message message) {
        user.setState(State.EDIT_SCHEDULE);
        userService.save(user);
//        log.info("Пользователь [" + user.getName() + " (" + user.getId() + ")] вызвал [" + message.getText() + "]");
        return Collections.singletonList(createMessageTemplate(user)
                .text(Html.strong("Выбери время") + "\n" +
                        "Редактор времени подразумевает следующее:\n" +
                        "Можно задать продолжительность дней, когда нужно пить таблетку и общее число дней в месяце\n" +
                        "Так же необходимо задать кол-во дней на протяжении которых уже пьется таблетка\n" +
                        Html.bold("Н") + " - " + Html.strong("\"Нужно\"") + " сколько нужно пить таблетку\n" +
                        Html.bold("В") + " - " + Html.strong("\"Всего\"") + " сколько всего дней в месяце\n" +
                        Html.bold("Т") + " - " + Html.strong("\"Текущий\"") + " текущий день выпитой таблетки\n\n" +
                        "<em>По всем вопросам обращаться:</em> @dmitryb_ov")
                .replyMarkup(new ChooseEditScheduleKeyboard(user).init().getReplyKeyboardMarkup())
                .build());
    }

    @Override
    public Command supportedCommand() {
        return Command.SELECT_EDIT_SCHEDULE;
    }
}
