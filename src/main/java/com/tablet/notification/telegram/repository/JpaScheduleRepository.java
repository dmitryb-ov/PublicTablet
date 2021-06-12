package com.tablet.notification.telegram.repository;

import com.tablet.notification.telegram.model.Schedule;
import com.tablet.notification.telegram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface JpaScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Schedule s WHERE s.user.id=:userId")
    int delete(@Param("userId") int userId);

    @Transactional
    @Modifying
    int deleteByUser(@Param("user") User user);

//    @Transactional
//    @Modifying
//    @Query("UPDATE Schedule s SET s=:schedule WHERE s.id=:scheduleId")
//    void update(@Param("schedule") Schedule schedule, @Param("scheduleId") int id);
}
