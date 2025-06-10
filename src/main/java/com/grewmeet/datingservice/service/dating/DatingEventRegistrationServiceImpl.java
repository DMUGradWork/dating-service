package com.grewmeet.datingservice.service.dating;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import com.grewmeet.datingservice.domain.user.User;
import com.grewmeet.datingservice.dto.event.DatingEventRegistrationRequestDTO;
import com.grewmeet.datingservice.exception.UserNotFoundException;
import com.grewmeet.datingservice.repository.DatingEventRepository;
import com.grewmeet.datingservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatingEventRegistrationServiceImpl implements DatingEventRegistrationService {

    private final DatingEventRepository datingEventRepository;
    private final UserRepository userRepository;

    @Override
    public DatingEvent registerEventBy(Long hostId, DatingEventRegistrationRequestDTO request) {
        User hostUser = userRepository.findById(hostId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + hostId + " not found"));

        DatingEvent newDatingEvent = DatingEvent.from(
                hostUser,
                request.title(),
                request.location(),
                request.eventDateTime(),
                request.maxMaleParticipantsCount(),
                request.maxFemaleParticipantsCount(),
                request.description());

        return datingEventRepository.save(newDatingEvent);
    }
}
