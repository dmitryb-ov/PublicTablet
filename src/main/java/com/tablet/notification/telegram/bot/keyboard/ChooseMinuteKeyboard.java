package com.tablet.notification.telegram.bot.keyboard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ChooseMinuteKeyboard extends AbstractBaseKeyboard<ChooseMinuteKeyboard> {
    private List<KeyboardRow> keyboardRows;
    private ReplyKeyboardMarkup replyKeyboardMarkup;

    public ChooseMinuteKeyboard() {
        this.keyboardRows = new ArrayList<>();
        this.replyKeyboardMarkup = new ReplyKeyboardMarkup();

        this.replyKeyboardMarkup.setSelective(true);
        this.replyKeyboardMarkup.setResizeKeyboard(true);
        this.replyKeyboardMarkup.setOneTimeKeyboard(false);
    }

    @Override
    public ChooseMinuteKeyboard init() {
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        KeyboardRow keyboardFourthRow = new KeyboardRow();
        KeyboardRow keyboardFifthRow = new KeyboardRow();
        KeyboardRow keyboardSixthRow = new KeyboardRow();
        KeyboardRow keyboardCancelRow = new KeyboardRow();

        for (int i = 0; i <= 9; i++) {
            keyboardFirstRow.add(String.valueOf(i));
        }

        for (int i = 10; i <= 19; i++) {
            keyboardSecondRow.add(String.valueOf(i));
        }

        for (int i = 20; i <= 29; i++) {
            keyboardThirdRow.add(String.valueOf(i));
        }

        for (int i = 30; i <= 39; i++) {
            keyboardFourthRow.add(String.valueOf(i));
        }

        for (int i = 40; i <= 49; i++) {
            keyboardFifthRow.add(String.valueOf(i));
        }

        for(int i = 50; i <= 59; i++){
            keyboardSixthRow.add(String.valueOf(i));
        }

        keyboardCancelRow.add("Отмена");


        keyboardRows.add(keyboardFirstRow);
        keyboardRows.add(keyboardSecondRow);
        keyboardRows.add(keyboardThirdRow);
        keyboardRows.add(keyboardFourthRow);
        keyboardRows.add(keyboardFifthRow);
        keyboardRows.add(keyboardSixthRow);
        keyboardRows.add(keyboardCancelRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return this;
    }

    @Override
    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        return this.replyKeyboardMarkup;
    }
}
