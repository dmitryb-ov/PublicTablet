package com.tablet.notification.telegram.bot.handler;

import com.tablet.notification.telegram.bot.command.Command;
import com.tablet.notification.telegram.bot.keyboard.MainKeyboard;
import com.tablet.notification.telegram.model.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static com.tablet.notification.telegram.bot.command.Command.HELP;

@Component
//@Log4j
public class HelpHandler extends AbstractBaseHandler {

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> operate(User user, Message message) {
//        log.info("Пользователь [" + user.getName() + " (" + user.getId() + ")] вызвал [" + message.getText() + "]");
        return Collections.singletonList(createMessageTemplate(user)
                .text("*Помощь по боту*\n\n" +
                        "/help - вызвать подсказку по командам\n" +
                        "/all (текст) - отправить всем сообщение [после 22:00 и до 7:00 отправка без звука]\n" +
                        "/addimages - обновить картинки\n" +
                        "/info - информация о расписании\n" +
                        "/gif - случайная гифка с животными\n")
                .replyMarkup(new MainKeyboard().init().getReplyKeyboardMarkup())
                .build());
    }

    @Override
    public Command supportedCommand() {
        return HELP;
    }
}
