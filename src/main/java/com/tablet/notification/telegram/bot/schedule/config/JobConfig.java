package com.tablet.notification.telegram.bot.schedule.config;

import com.tablet.notification.telegram.bot.schedule.NotificationJob;
import com.tablet.notification.telegram.model.Schedule;
import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.TimeZone;
import java.util.stream.Collectors;

@Slf4j
@Component
@EnableScheduling
@EnableAsync
public class JobConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    @Scheduled(cron = "0 */15 * ? * *")
    @Async
    public void scheduler() {
        try {
            scheduler.clear();
            for (User user : userService.getUsers()) {
                for (Schedule schedule : user.getScheduleList()
                        .stream()
                        .filter(schedule ->
                                !schedule.isDrink()
                                        && schedule.getCurrentDayDrink() < schedule.getMustDrinkDays())
                        .collect(Collectors.toList())
                ) {

                    String CRON = "0 " + schedule.getMinutes() + "/10 " + schedule.getHour() + "-23 ? * * *";
//                    String CRON = "0 " + schedule.getMinutes() + "/2 " + schedule.getHour() + "-23 ? * * *";

                    JobDataMap jobDataMap = new JobDataMap();
                    jobDataMap.put("schedule_model", schedule);
                    jobDataMap.put("user_model", user);

                    scheduler.scheduleJob(
                            JobBuilder.newJob(NotificationJob.class)
                                    .withIdentity("Job_id " + schedule.getId())
                                    .build(),

                            TriggerBuilder.newTrigger()
                                    .withIdentity("Trigger_id " + schedule.getId())
                                    .withSchedule(CronScheduleBuilder
                                            .cronSchedule(CRON)
                                            .inTimeZone(TimeZone.getTimeZone("Europe/Moscow")))
                                    .usingJobData(jobDataMap)
                                    .build()
                    );
                    scheduler.start();
                }
            }


        } catch (SchedulerException e) {
            log.error("Job Confing exception " + e);
        }
    }
}
