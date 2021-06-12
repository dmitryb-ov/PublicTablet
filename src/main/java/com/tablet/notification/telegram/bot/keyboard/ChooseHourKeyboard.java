package com.tablet.notification.telegram.bot.keyboard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ChooseHourKeyboard extends AbstractBaseKeyboard<ChooseHourKeyboard> {
    private List<KeyboardRow> keyboardRows;
    private ReplyKeyboardMarkup replyKeyboardMarkup;


    public ChooseHourKeyboard() {
        this.keyboardRows = new ArrayList<>();
        this.replyKeyboardMarkup = new ReplyKeyboardMarkup();

        this.replyKeyboardMarkup.setSelective(true);
        this.replyKeyboardMarkup.setResizeKeyboard(true);
        this.replyKeyboardMarkup.setOneTimeKeyboard(false);
    }

    @Override
    public ChooseHourKeyboard init() {

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        KeyboardRow keyboardFourthRow = new KeyboardRow();
        KeyboardRow keyboardCancelRow = new KeyboardRow();

        for (int i = 0; i <= 5; i++) {
            keyboardFirstRow.add(String.valueOf(i));
        }
        for (int i = 6; i <= 11; i++) {
            keyboardSecondRow.add(String.valueOf(i));
        }
        for (int i = 12; i <= 17; i++) {
            keyboardThirdRow.add(String.valueOf(i));
        }
        for (int i = 18; i <= 23; i++) {
            keyboardFourthRow.add(String.valueOf(i));
        }
        keyboardCancelRow.add("Отмена");

        keyboardRows.add(keyboardFirstRow);
        keyboardRows.add(keyboardSecondRow);
        keyboardRows.add(keyboardThirdRow);
        keyboardRows.add(keyboardFourthRow);
        keyboardRows.add(keyboardCancelRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return this;
    }

    @Override
    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        return this.replyKeyboardMarkup;
    }
}
