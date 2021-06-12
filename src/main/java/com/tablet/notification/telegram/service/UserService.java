package com.tablet.notification.telegram.service;

import com.tablet.notification.telegram.model.User;
import com.tablet.notification.telegram.repository.JpaUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.tablet.notification.telegram.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional
public class UserService {
    private JpaUserRepository userRepository;


    public UserService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User get(Long chatId) {
        return checkNotFoundWithId(userRepository.getByChatId(chatId).orElse(null), chatId);
    }

    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        return userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
}
