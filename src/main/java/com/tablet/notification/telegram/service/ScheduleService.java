package com.tablet.notification.telegram.service;

import com.tablet.notification.telegram.model.Schedule;
import com.tablet.notification.telegram.repository.JpaScheduleRepository;
import com.tablet.notification.telegram.repository.JpaUserRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {
    private JpaScheduleRepository scheduleRepository;

    private JpaUserRepository userRepository;

    public ScheduleService(JpaScheduleRepository scheduleRepository, JpaUserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    public void clear(int userId) {
        scheduleRepository.delete(userId);
    }

    public Schedule get(int id, int userId) {
        return scheduleRepository.findById(id)
                .filter(meal -> meal.getUser().getId() == userId)
                .orElse(null);
    }

    @Transactional
    @Modifying(clearAutomatically = true)
    public Schedule addSchedule(Schedule schedule, int userId) {
        schedule.setUser(userRepository.getOne(userId));
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllByDrinkTrueState() {
        return scheduleRepository
                .findAll()
                .stream()
                .filter(Schedule::isDrink)
                .collect(Collectors.toList());
    }

    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    public void deleteSchedule(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }

    @Transactional
    @Modifying(clearAutomatically = true)
    public void flush() {
        scheduleRepository.flush();
    }

}
