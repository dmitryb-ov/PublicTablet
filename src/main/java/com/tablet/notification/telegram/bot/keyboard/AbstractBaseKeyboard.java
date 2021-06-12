package com.tablet.notification.telegram.bot.keyboard;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Slf4j
public abstract class AbstractBaseKeyboard<T> {

    public abstract T init();

    public abstract ReplyKeyboardMarkup getReplyKeyboardMarkup();
}
