package com.tablet.notification.telegram.bot.handler;

import com.tablet.notification.telegram.bot.command.Command;
import com.tablet.notification.telegram.bot.schedule.GetImageJob;
import com.tablet.notification.telegram.model.User;
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
public class AddImagesHandler extends AbstractCommandHandler {

    @Autowired
    private GetImageJob getImageJob;

    public AddImagesHandler(UserService userService) {
        super(userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> operate(User user, Message message) {
        getImageJob.scheduleGetImage();
//        log.info("Пользователь [" + user.getName() + " (" + user.getId() + ")] вызвал [" + message.getText() + "]");
        return Collections.singletonList(createMessageTemplate(user)
                .text("Картинки обновлены")
                .build());
    }

    @Override
    public Command supportedCommand() {
        return Command.ADD_IMAGES;
    }
}
