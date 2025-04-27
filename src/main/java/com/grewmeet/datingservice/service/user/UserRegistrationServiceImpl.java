package com.grewmeet.datingservice.service.user;

import com.grewmeet.datingservice.domain.user.User;
import com.grewmeet.datingservice.dto.user.UserRegistrationRequestDTO;
import com.grewmeet.datingservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepository userRepository;

    @Override
    public User findExistingOrRegisterNew(UserRegistrationRequestDTO request) {
        return userRepository.findById(request.id())
                .orElseGet(() -> userRepository.save(User.create(request.id(), request.name())));
    }
}
