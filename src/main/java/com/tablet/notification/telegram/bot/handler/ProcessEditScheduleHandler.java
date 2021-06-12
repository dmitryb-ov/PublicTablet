package com.tablet.notification.telegram.bot.handler;

import com.tablet.notification.telegram.bot.State;
import com.tablet.notification.telegram.bot.command.Command;
import com.tablet.notification.telegram.bot.schedule.config.JobConfig;
import com.tablet.notification.telegram.model.Schedule;
import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.service.ScheduleService;
import com.tablet.notification.telegram.service.UserService;
import com.tablet.notification.telegram.util.Html;
import com.tablet.notification.telegram.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
//@Log4j
public class ProcessEditScheduleHandler extends AbstractScheduleHandler {
    private static final int RANGE_MIN = 0;
    private static final int RANGE_MAX = 31;

    @Autowired
    private UserService userService;

    @Autowired
    private JobConfig jobConfig;

    public ProcessEditScheduleHandler(ScheduleService scheduleService) {
        super(scheduleService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> operate(User user, Message message) {
        String[] messages = message.getText().split(" ");
        if (messages.length == 3) {

            if (
                    ValidationUtil.isNumeric(messages[0])
                            || ValidationUtil.isNumeric(messages[1])
                            || ValidationUtil.isNumeric(messages[2])
            ) {
                int mdd = Integer.parseInt(messages[0]);
                int ad = Integer.parseInt(messages[1]);
                int cdd = Integer.parseInt(messages[2]);

                Schedule schedule = user.getScheduleList()
                        .stream()
                        .filter(sc -> sc.getId() == Integer.parseInt(user.getMetaInfo())
                                && sc.getUser().getId().intValue() == user.getId().intValue())
                        .collect(Collectors.toList()).get(0);
                if (schedule != null) {

                    if (
                            ValidationUtil.isInRange(mdd, RANGE_MIN, RANGE_MAX)
                                    && ValidationUtil.isInRange(ad, RANGE_MIN, RANGE_MAX)
                                    && ValidationUtil.isInRange(cdd, RANGE_MIN, RANGE_MAX)) {
                        if (mdd < ad || (mdd == 0 && ad == 0)) {

                            schedule.setMustDrinkDays(mdd);
                            schedule.setAllDays(ad);
                            if (mdd == 0 && ad == 0) {
                                schedule.setCurrentDayDrink(0);
                            } else {
                                schedule.setCurrentDayDrink(cdd);
                            }

                            Schedule resultSchedule = scheduleService.addSchedule(schedule, user.getId());
                            scheduleService.flush();

                            user.setMetaInfo(null);
                            user.setState(State.START);
                            userService.save(user);

                            StringBuilder stringBuilder = new StringBuilder();
                            if (mdd == 0 && ad == 0) {
                                stringBuilder.append("Курс у напоминания ID ").append(Html.bold(resultSchedule.getId())).append(" обнулен");
                            } else {
                                stringBuilder.append("Напоминание ID ").append(Html.bold(resultSchedule.getId())).append(" успешно изменено").append("\n")
                                        .append(Html.bold("Нужно пить: ")).append(resultSchedule.getMustDrinkDays()).append("\n")
                                        .append(Html.bold("Всего дней в курсе: ")).append(resultSchedule.getAllDays()).append("\n")
                                        .append(Html.bold("Сколько дней прошло с момента начала курса: ")).append(resultSchedule.getCurrentDayDrink()).append("\n");
                            }

                            jobConfig.scheduler();
//                            log.info("Пользователь [" + user.getName() + " (" + user.getId() + ")] вызвал [" + message.getText() + "]");
                            return Collections.singletonList(createMessageTemplate(user)
                                    .text(stringBuilder.toString())
                                    .build());
                        } else {
                            return Collections.singletonList(createMessageTemplate(user)
                                    .text("Первое число не должно быть больше второго!")
                                    .build());
                        }
                    } else {
                        return Collections.singletonList(createMessageTemplate(user)
                                .text("Неверный формат данных, числа должны быть от 0 до 31!")
                                .build());

                    }
                } else {
                    user.setState(State.START);
                    user.setMetaInfo(null);
                    userService.save(user);
                    return Collections.singletonList(createMessageTemplate(user)
                            .text("Что-то пошло не так, попробуйте еще раз")
                            .build()
                    );
                }
            } else {
                return Collections.singletonList(createMessageTemplate(user)
                        .text("Неверный формат сообщения, нужно ввести три числа без запятых и прочего!")
                        .build()
                );
            }
        } else {
            return Collections.singletonList(createMessageTemplate(user)
                    .text("Неверный формат сообщения!")
                    .build()
            );
        }
    }

    @Override
    public Command supportedCommand() {
        return Command.PROCESS_EDIT_SCHEDULE;
    }
}
