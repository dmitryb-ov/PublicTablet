package com.tablet.notification.telegram.bot.handler;

import com.tablet.notification.telegram.bot.command.Command;
import com.tablet.notification.telegram.bot.keyboard.MainKeyboard;
import com.tablet.notification.telegram.bot.schedule.config.JobConfig;
import com.tablet.notification.telegram.model.Image;
import com.tablet.notification.telegram.model.ImageType;
import com.tablet.notification.telegram.model.Schedule;
import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.service.ImageService;
import com.tablet.notification.telegram.service.ScheduleService;
import com.tablet.notification.telegram.service.UserService;
import com.tablet.notification.telegram.util.Html;
import com.tablet.notification.telegram.util.WordFormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
//@Log4j
public class DrinkHandler extends AbstractCommandHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private JobConfig jobConfig;

    public DrinkHandler(UserService userService) {
        super(userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> operate(User user, Message message) {
        Schedule schedule = user.getScheduleList()
                .stream()
                .filter(sc -> sc.getId()
                        == Integer.parseInt(user.getMetaInfo()))
                .collect(Collectors.toList()).get(0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Супер!");
        if (schedule.getMustDrinkDays() != 0 && schedule.getAllDays() != 0) {
            schedule.setCurrentDayDrink(schedule.getCurrentDayDrink() + 1);
            stringBuilder.append("\n").append("Ты уже пьешь ")
                    .append(Html.bold(schedule.getCurrentDayDrink())).append(" ")
                    .append(WordFormatterUtil.getCurrentDayWordLeaningNumber(schedule.getCurrentDayDrink())).append(" ")
                    .append("из ")
                    .append(Html.bold(schedule.getMustDrinkDays())).append(" ")
                    .append(WordFormatterUtil.getMustDayWordLeaningNumber(schedule.getMustDrinkDays()));
        } else {
            stringBuilder.append("\n").append(Html.strong("Ты можешь задать расписание уведомлений для твоей таблетки, нажми \"Редактировать время\""));
        }

        schedule.setDrink(true);
        user.setMetaInfo(null);
        scheduleService.addSchedule(schedule, user.getId());
        userService.save(user);
        jobConfig.scheduler();

//        log.info("Пользователь [" + user.getName() + " (" + user.getId() + ")] выпил таблетку");

        List<Image> images = imageService.getAllImages();
        Random random = new Random();
        try {
            Image randomImage = images.get(random.nextInt(images.size()));
            if (randomImage.getType() == ImageType.GIF) {
//                InputStream inputStream = new URL(randomImage.getImageUrl()).openStream();
//                InputFile inputFile = new InputFile();
//                inputFile.setMedia(inputStream, randomImage.getImageName());
                System.err.println("GIF");

                return Collections.singletonList(createMessageTemplate(user)
                        .text(stringBuilder.toString() + "\n" + randomImage.getImageUrl())
                        .replyMarkup(new MainKeyboard().init().getReplyKeyboardMarkup())
                        .build());

            } else if (randomImage.getType() == ImageType.OTHER) {
                InputStream inputStream = new URL(randomImage.getImageUrl()).openStream();
                InputFile inputFile = new InputFile();
                inputFile.setMedia(inputStream, randomImage.getImageName());
                System.err.println("OTHER");

                return Collections.singletonList(createPhotoMessageTemplate(user)
                        .caption(stringBuilder.toString())
                        .photo(inputFile)
                        .replyMarkup(new MainKeyboard().init().getReplyKeyboardMarkup())
                        .build());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            return Collections.singletonList(createPhotoMessageTemplate(user)
                    .caption(stringBuilder.toString())
                    .replyMarkup(new MainKeyboard().init().getReplyKeyboardMarkup())
                    .build());
        }

        return Collections.singletonList(createMessageTemplate(user)
                .text(stringBuilder.toString())
                .replyMarkup(new MainKeyboard().init().getReplyKeyboardMarkup())
                .build());
    }

    @Override
    public Command supportedCommand() {
        return Command.DRINK;
    }
}
