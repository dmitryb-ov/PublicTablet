package com.tablet.notification.telegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class App {
    public static void main(String[] args) {
        try {
            new TelegramBotsApi(DefaultBotSession.class);
            SpringApplication.run(App.class, args);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
