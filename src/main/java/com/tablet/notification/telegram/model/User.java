package com.tablet.notification.telegram.model;

import com.tablet.notification.telegram.bot.State;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "spring_tablet_users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "chat_id", name = "users_unique_chatid_idx")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends AbstractBaseEntity {
    @Column(name = "chat_id", unique = true, nullable = false)
    @NotNull
    private Long chatId;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @Column(name = "admin", columnDefinition = "boolean default false")
    private boolean isAdmin;

    @Column(name = "bot_state", nullable = false)
    @NotNull
    private State state;

    @Column(name = "meta_inf")
    private String metaInfo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    @OrderBy("hour DESC")
    private List<Schedule> scheduleList;

    public User(Long chatId, String name){
        this.chatId = chatId;
        this.name = name;
        this.state = State.START;
    }
}
