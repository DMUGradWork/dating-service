package com.grewmeet.datingservice.service.user;

import com.grewmeet.datingservice.domain.user.User;
import com.grewmeet.datingservice.dto.user.UserRegistrationRequestDTO;

public interface UserQualificationService {
    boolean accessingToDatingEventsIsApprovedFor(UserRegistrationRequestDTO request);
}

