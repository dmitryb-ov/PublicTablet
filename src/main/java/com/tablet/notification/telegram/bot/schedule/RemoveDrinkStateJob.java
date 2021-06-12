package com.tablet.notification.telegram.bot.schedule;

import com.tablet.notification.telegram.model.Schedule;
import com.tablet.notification.telegram.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@EnableScheduling
@EnableAsync
public class RemoveDrinkStateJob {

    @Autowired
    private ScheduleService scheduleService;

    @Scheduled(cron = "0 0 2 ? * *", zone = "Europe/Moscow")
    @Async
    void removeDrinkState() {
        List<Schedule> schedules = scheduleService.getAllByDrinkTrueState()
                .stream()
                .peek(schedule -> schedule.setDrink(false))
                .collect(Collectors.toList());
        for(Schedule schedule: schedules){
            scheduleService.addSchedule(schedule, schedule.getUser().getId());
        }
    }
}
