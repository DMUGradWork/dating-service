package com.grewmeet.datingservice.service.user;

import com.grewmeet.datingservice.dto.user.UserRegistrationRequestDTO;
import com.grewmeet.datingservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQualificationServiceImpl implements UserQualificationService {
// 사용 안함

    private final UserRepository userRepository;

    @Override
    public boolean accessingToDatingEventsIsApprovedFor(UserRegistrationRequestDTO request) {
//        if(userRepository.existsById(request.id())) {
//            return true;
//        }
        return false;
    }
}
