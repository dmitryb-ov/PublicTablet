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
public class DrinkKeyboard extends AbstractBaseKeyboard<DrinkKeyboard> {
    private List<KeyboardRow> keyboardRows;
    private ReplyKeyboardMarkup replyKeyboardMarkup;

    public DrinkKeyboard() {
        this.keyboardRows = new ArrayList<>();
        this.replyKeyboardMarkup = new ReplyKeyboardMarkup();

        this.replyKeyboardMarkup.setSelective(true);
        this.replyKeyboardMarkup.setResizeKeyboard(true);
        this.replyKeyboardMarkup.setOneTimeKeyboard(false);
    }

    @Override
    public DrinkKeyboard init() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(Button.DRINK.toString());
        keyboardRows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return this;
    }

    @Override
    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        return replyKeyboardMarkup;
    }
}
