package com.tablet.notification.telegram.bot.keyboard;

import com.tablet.notification.telegram.model.Schedule;
import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.util.BotUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@ComponentScan
@Slf4j
public class ChooseDeleteScheduleKeyboard extends AbstractBaseKeyboard<ChooseDeleteScheduleKeyboard> {
    private List<KeyboardRow> keyboardRows;
    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private List<Schedule> schedules;

    public ChooseDeleteScheduleKeyboard(User user) {
        this.schedules = user.getScheduleList();
        this.keyboardRows = new ArrayList<>();
        this.replyKeyboardMarkup = new ReplyKeyboardMarkup();

        this.replyKeyboardMarkup.setSelective(true);
        this.replyKeyboardMarkup.setResizeKeyboard(true);
        this.replyKeyboardMarkup.setOneTimeKeyboard(false);
    }

    @Override
    public ChooseDeleteScheduleKeyboard init() {
        KeyboardRow keyboardCancelRow = new KeyboardRow();
        for (Schedule schedule : schedules) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(String.format("(%d) Напоминание %d:%s", schedule.getId(), schedule.getHour(), BotUtil.formatMinutes(schedule.getMinutes())));
            keyboardRows.add(keyboardRow);
        }
        keyboardCancelRow.add("Отмена");

        keyboardRows.add(keyboardCancelRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return this;
    }

    @Override
    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        return replyKeyboardMarkup;
    }
}
