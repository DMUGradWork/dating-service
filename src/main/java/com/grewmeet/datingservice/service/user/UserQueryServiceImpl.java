package com.grewmeet.datingservice.service.user;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import com.grewmeet.datingservice.domain.dating.DatingParticipant;
import com.grewmeet.datingservice.domain.user.User;
import com.grewmeet.datingservice.dto.event.DatingEventSummaryDto;
import com.grewmeet.datingservice.exception.UserNotFoundException;
import com.grewmeet.datingservice.repository.DatingEventRepository;
import com.grewmeet.datingservice.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;
    private final DatingEventRepository eventRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user" + id + "not found"));
    }

    public List<DatingEventSummaryDto> getUserDatingEvents(Long userId) {
        User user = getUserById(userId);
        return user.getParticipants().stream()
                .map(this::toParticipatedEventSummaryDto)
                .toList();
    }

    private DatingEventSummaryDto toParticipatedEventSummaryDto(DatingParticipant participant) {
        DatingEvent datingEvent = participant.getDatingEvent();
        return new DatingEventSummaryDto(
                datingEvent.getId(),
                datingEvent.getTitle(),
                datingEvent.getEventDateTime());
    }
}
