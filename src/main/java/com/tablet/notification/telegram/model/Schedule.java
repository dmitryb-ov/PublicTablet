package com.tablet.notification.telegram.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Table(name = "spring_tablet_schedule", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "hour", "minutes"}, name = "schedule_unique_user_time_idx")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedule extends AbstractBaseEntity {

    @Column(name = "hour", nullable = false)
    @Range(min = 0, max = 23)
    private int hour;

    @Column(name = "minutes", nullable = false, columnDefinition = "integer default 0")
    @Range(min = 0, max = 59)
    private int minutes;

    @Column(name = "drink", columnDefinition = "boolean default false")
    private boolean isDrink;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = CASCADE)
    @NotNull
    private User user;

    @Column(name = "must_drink_days", columnDefinition = "integer default 0")
    @Range(min = 0, max = 31)
    private int mustDrinkDays;

    @Column(name = "all_days", columnDefinition = "integer default 0")
    @Range(min = 0, max = 31)
    private int allDays;

    @Column(name = "current_day_drink", columnDefinition = "integer default 0")
    @Range(min = 0, max = 31)
    private int currentDayDrink;

    public Schedule(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
        this.isDrink = false;
    }
}
