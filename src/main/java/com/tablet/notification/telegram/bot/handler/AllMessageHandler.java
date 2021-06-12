package com.tablet.notification.telegram.bot.handler;

import com.tablet.notification.telegram.bot.command.Command;
import com.tablet.notification.telegram.bot.keyboard.MainKeyboard;
import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.util.BotUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
//@Log4j
public class AllMessageHandler extends AbstractBaseHandler {

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> operate(User user, Message message) {
//        log.info("Пользователь [" + user.getName() + " (" + user.getId() + ")] вызвал [" + message.getText() + "]");
        return Collections.singletonList(createMessageTemplate(user)
                .text("Сообщения отправлены")
                .build());
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> multiplyOperate(List<User> users, Message message) {
        List<SendMessage> list = new ArrayList<>();
        boolean disableNotification = false;
        if ((BotUtil.getHourMoscowTime() >= 22 && BotUtil.getHourMoscowTime() <= 23)
                || (BotUtil.getHourMoscowTime() >= 0 && BotUtil.getHourMoscowTime() <= 7)) {
            disableNotification = true;
        }

        for (User myUser : users) {
            list.add(createMessageTemplate(myUser)
                    .text(message.getText().substring("/ALL".length()))
                    .disableNotification(disableNotification)
                    .replyMarkup(new MainKeyboard().init().getReplyKeyboardMarkup())
                    .build());
        }

        return Collections.unmodifiableList(list);
    }

    @Override
    public Command supportedCommand() {
        return Command.ALL_MESSAGE;
    }
}
