package com.tablet.notification.telegram.bot.schedule;

import com.tablet.notification.telegram.model.Schedule;
import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.service.ScheduleService;
import com.tablet.notification.telegram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@EnableScheduling
@EnableAsync
public class DayUpdaterForSchedule {
    private static boolean val = false;
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UserService userService;

    @Autowired
    @Scheduled(cron = "0 55 23 ? * *", zone = "Europe/Moscow")
//    @Scheduled(cron = "0 22 0 ? * *")
    @Async
    public void update() {
        if (val) {
            List<User> userList = userService.getUsers();
            for (User user : userList) {
                List<Schedule> schedules = user.getScheduleList()
                        .stream()
                        .filter(schedule -> schedule.getMustDrinkDays() != 0 && schedule.getAllDays() != 0)
                        .collect(Collectors.toList());

                for (Schedule schedule : schedules) {

                    if (schedule.getCurrentDayDrink() > schedule.getMustDrinkDays()) {
                        schedule.setCurrentDayDrink(schedule.getCurrentDayDrink() + 1);
                        scheduleService.addSchedule(schedule, user.getId());
                    }

                    if (schedule.getCurrentDayDrink() == schedule.getAllDays()) {
                        schedule.setCurrentDayDrink(0);
                        schedule.setDrink(true);
                        scheduleService.addSchedule(schedule, user.getId());
                    }
                }
            }
        } else {
            val = true;
        }
    }
}
