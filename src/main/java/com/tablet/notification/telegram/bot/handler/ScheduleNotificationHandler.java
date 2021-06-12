package com.tablet.notification.telegram.bot.handler;

import com.ibm.icu.text.Transliterator;
import com.tablet.notification.telegram.bot.Bot;
import com.tablet.notification.telegram.bot.UpdateReceiver;
import com.tablet.notification.telegram.bot.keyboard.DrinkKeyboard;
import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.util.Html;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.context.annotation.ComponentScan;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

//TODO don touch
@ComponentScan
public class ScheduleNotificationHandler extends Bot {

    @Override
    public String getBotUsername() {
        //Pub
//        return "";
        //Test
        return "";
    }

    @Override
    public String getBotToken() {
        //Pub
//        return "";
        //Test
        return "";
    }

    public ScheduleNotificationHandler(UpdateReceiver updateReceiver) {
        super(updateReceiver);
    }

    public void sendNotificationMessage(User user) {
        final String CYRILLIC_TO_LATIN = "Latin-Russian/BGN";
        Transliterator transliterator = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        String name = transliterator.transliterate(user.getName());
        SendMessage sendMessage = SendMessage.builder()
                .chatId(String.valueOf(user.getChatId()))
                .text(Html.bold(name) + ", пора пить таблетку " + EmojiParser.parseToUnicode(":alarm_clock:"))
                .parseMode(ParseMode.HTML)
                .replyMarkup(new DrinkKeyboard().init().getReplyKeyboardMarkup())
                .build();
        executeWithExceptionCheck(sendMessage);
    }
}
