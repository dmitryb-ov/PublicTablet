package com.tablet.notification.telegram.bot.keyboard.keyboard_button;

import com.vdurmont.emoji.EmojiParser;

public enum Button {
    ADD_TIME(EmojiParser.parseToUnicode(":heavy_plus_sign:") + " Добавить время"),
    SELECT_DELETE_TIME(EmojiParser.parseToUnicode(":x:") + " Удалить время"),
    CANCEL("Отмена"),
    DRINK("Я выпила" + EmojiParser.parseToUnicode(":pill:")),
    EDIT_TIME(EmojiParser.parseToUnicode(":gear:") + " Редактировать время"),
    ;

    private String name;

    Button(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
