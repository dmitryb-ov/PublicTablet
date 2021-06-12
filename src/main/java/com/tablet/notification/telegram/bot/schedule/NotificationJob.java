package com.tablet.notification.telegram.bot.schedule;

import com.tablet.notification.telegram.bot.UpdateReceiver;
import com.tablet.notification.telegram.bot.handler.ScheduleNotificationHandler;
import com.tablet.notification.telegram.model.Schedule;
import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@DisallowConcurrentExecution
public class NotificationJob extends QuartzJobBean {

    @Autowired
    private UpdateReceiver updateReceiver;

    @Autowired
    private UserService userService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Schedule schedule = (Schedule) context.getTrigger().getJobDataMap().get("schedule_model");
        User user = (User) context.getTrigger().getJobDataMap().get("user_model");
        log.info("Job ** {} ** starting @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
        user.setMetaInfo(String.valueOf(schedule.getId()));
        ScheduleNotificationHandler scheduleNotificationHandler = new ScheduleNotificationHandler(updateReceiver);
        scheduleNotificationHandler.sendNotificationMessage(user);
        userService.save(user);
    }
}
