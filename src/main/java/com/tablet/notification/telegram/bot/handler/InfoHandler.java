package com.tablet.notification.telegram.bot.handler;

import com.tablet.notification.telegram.bot.command.Command;
import com.tablet.notification.telegram.model.Schedule;
import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static com.tablet.notification.telegram.util.Html.*;

//@Log4j
@Component
public class InfoHandler extends AbstractCommandHandler {
    private final String HIDDEN_DEL = del("hidden");

    @Value("${bot.admin}")
    private String botAdmin;

    public InfoHandler(UserService userService) {
        super(userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> operate(User user, Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(strong("Информация по расписаниям")).append("\n\n");
        for (User u : userService.getUsers()) {
            stringBuilder
                    .append(i("Пользователь")).append("\n")
                    .append(bold("Name: ")).append(u.getName()).append("\n")
                    .append(bold("ID: ")).append(u.getId()).append("\n")
                    .append(bold("Chat id: ")).append(user.getChatId().toString().equals(botAdmin) ? u.getChatId() : HIDDEN_DEL).append("\n")
                    .append(bold("State: ")).append(user.getChatId().toString().equals(botAdmin) ? u.getState().toString() : HIDDEN_DEL).append("\n")
                    .append(bold("Meta: ")).append(user.getChatId().toString().equals(botAdmin) ? u.getMetaInfo() : HIDDEN_DEL).append("\n\n")
                    .append(i("Расписания")).append("\n");
            for (Schedule s : u.getScheduleList()) {
                stringBuilder
                        .append(bold("ID: ")).append(s.getId()).append("\n")
                        .append(bold("Time: ")).append(s.getHour()).append(":").append(s.getMinutes()).append("\n")
                        .append(bold("Drink state: ")).append(s.isDrink() ? "Yes" : "No").append("\n")
                        .append(bold("Must day drink: ")).append(s.getMustDrinkDays()).append("\n")
                        .append(bold("All drink days: ")).append(s.getAllDays()).append("\n")
                        .append(bold("Current day drink: ")).append(s.getCurrentDayDrink()).append("\n\n");
            }

            stringBuilder.append("-------------------------\n\n");
        }

//        log.info("Пользователь [" + user.getName() + " (" + user.getId() + ")] вызвал [" + message.getText() + "]");
        return Collections.singletonList(createMessageTemplate(user)
                .text(stringBuilder.toString())
                .build());
    }

    @Override
    public Command supportedCommand() {
        return Command.INFO;
    }
}
