package com.tablet.notification.telegram.bot.keyboard;

import com.tablet.notification.telegram.bot.keyboard.keyboard_button.Button;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class MainKeyboard extends AbstractBaseKeyboard<MainKeyboard> {
    private List<KeyboardRow> keyboardRows;
    private KeyboardRow keyboardFirstRow;
    private KeyboardRow keyboardSecondRow;
    private ReplyKeyboardMarkup replyKeyboardMarkup;

    public MainKeyboard() {
        this.keyboardRows = new ArrayList<>();
        this.replyKeyboardMarkup = new ReplyKeyboardMarkup();
        this.replyKeyboardMarkup.setSelective(true);
        this.replyKeyboardMarkup.setResizeKeyboard(true);
        this.replyKeyboardMarkup.setOneTimeKeyboard(false);

        this.keyboardFirstRow = new KeyboardRow();
        this.keyboardSecondRow = new KeyboardRow();
    }

    @Override
    public MainKeyboard init() {
        keyboardFirstRow.clear();
        keyboardFirstRow.add(Button.ADD_TIME.toString());
        keyboardFirstRow.add(Button.SELECT_DELETE_TIME.toString());
        keyboardSecondRow.add(Button.EDIT_TIME.toString());
        keyboardRows.add(keyboardFirstRow);
        keyboardRows.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return this;
    }

    @Override
    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        return replyKeyboardMarkup;
    }
}
