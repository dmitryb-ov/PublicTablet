package com.tablet.notification.telegram.bot;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.List;

//TODO dont touch
@Component
@PropertySource("classpath:bot/bot.properties")
@Slf4j
public class Bot extends TelegramLongPollingBot {

        @Value("${bot.name.test}")
//    @Value("${bot.name.war}")
    @Getter
    private String botUsername;

        @Value("${bot.token.test}")
//    @Value("${bot.token.war}")
    @Getter
    private String botToken;

    @Value("${bot.admin}")
    private String botAdmin;

    private final UpdateReceiver updateReceiver;

    public Bot(UpdateReceiver updateReceiver) {
        this.updateReceiver = updateReceiver;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        List<PartialBotApiMethod<? extends Serializable>> messagesToSend = updateReceiver.handle(update);

        if (messagesToSend != null || !messagesToSend.isEmpty()) {
            messagesToSend.forEach(response -> {
                if (response instanceof SendMessage) {
                    executeWithExceptionCheck((SendMessage) response);
                }

                if (response instanceof SendAnimation) {
                    executeSendAnimationWithExceptionCheck((SendAnimation) response);
                }

                if (response instanceof SendPhoto) {
                    executeSendPhotoWithExceptionCheck((SendPhoto) response);
                }

                if (response instanceof SendDocument) {
                    executeSendDocumentWithExceptionCheck((SendDocument) response);
                }
            });
        }
    }

    public void executeSendPhotoWithExceptionCheck(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
            log.debug("Executed {}", sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Exception while sending photo message to user: {}", e.getMessage());
        }
    }

    public void executeSendAnimationWithExceptionCheck(SendAnimation sendAnimation) {
        try {
            execute(sendAnimation);
            log.debug("Executed {}", sendAnimation);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Exception while sending animation message to user: {}", e.getMessage());
        }
    }

    public void executeSendDocumentWithExceptionCheck(SendDocument sendDocument) {
        try {
            execute(sendDocument);
            log.debug("Executed {}", sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Exception while sending document message to user: {}", e.getMessage());
        }
    }

    public void executeWithExceptionCheck(SendMessage sendMessage) {
        try {
            execute(sendMessage);
            log.debug("Executed {}", sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Exception while sending text message to user: {}", e.getMessage());
        }
    }

    public void sendStartReport() {
        executeWithExceptionCheck(SendMessage.builder()
                .chatId(botAdmin)
                .text("Bot start up is successful")
                .build());
        log.info("Start report sent to Admin");
    }
}
