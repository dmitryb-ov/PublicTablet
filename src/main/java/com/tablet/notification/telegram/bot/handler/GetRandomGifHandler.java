package com.tablet.notification.telegram.bot.handler;

import com.tablet.notification.telegram.bot.command.Command;
import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.service.UserService;
import com.tablet.notification.telegram.util.GiphyUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
//@Log4j
public class GetRandomGifHandler extends AbstractCommandHandler {

    public GetRandomGifHandler(UserService userService) {
        super(userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> operate(User user, Message message) {
        GiphyUtil giphyUtil = new GiphyUtil();
//        log.info("Пользователь [" + user.getName() + " (" + user.getId() + ")] вызвал [" + message.getText() + "]");
        return Collections.singletonList(createMessageTemplate(user)
                .text(giphyUtil.getStringGifUrl())
                .build());
    }

    @Override
    public Command supportedCommand() {
        return Command.GIF;
    }
}
