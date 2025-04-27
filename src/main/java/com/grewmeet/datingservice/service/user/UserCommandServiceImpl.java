package com.grewmeet.datingservice.service.user;

import com.grewmeet.datingservice.domain.user.User;
import com.grewmeet.datingservice.exception.UserNotFoundException;
import com.grewmeet.datingservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private UserRepository userRepository;

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
