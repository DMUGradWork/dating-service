package com.grewmeet.datingservice.service.dating;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import com.grewmeet.datingservice.domain.dating.DatingParticipant;
import com.grewmeet.datingservice.domain.user.User;
import com.grewmeet.datingservice.dto.event.DatingEventResponseNew;
import com.grewmeet.datingservice.repository.DatingEventRepository;
import com.grewmeet.datingservice.repository.ParticipantRepository;
import com.grewmeet.datingservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class DatingEventParticipantServiceImpl implements DatingEventParticipantService {

    private final UserRepository userRepository;
    private final DatingEventRepository eventRepository;
    private final ParticipantRepository participationRepository;
    private final ParticipationVerificationService participationVerificationService;

    @Override
    public DatingEventResponseNew joinAt(Long eventId, Long userId) {
        User user = getUserById(userId);
        DatingEvent event = getEventById(eventId);

        DatingParticipant requested = createParticipationRequest(event, user);
        validateBeforeRegister(requested,event,user);

        DatingParticipant completedParticipation = confirmParticipation(requested, event, user);
        participationRepository.save(completedParticipation);

        return DatingEventResponseNew.from(completedParticipation.getDatingEvent());
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    }

    private DatingEvent getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found: " + eventId));
    }

    @Override
    public void leaveAt(Long eventId, Long userId) {
        User user = getUserById(userId);
        DatingEvent event = getEventById(eventId);

        DatingParticipant participant = participationRepository
                .findByUserAndDatingEvent(user, event)
                .orElseThrow(() -> new IllegalStateException("No participation found"));

        user.removeParticipation(participant);
        event.removeParticipation(participant);
        participationRepository.delete(participant);

    }

    private void validateBeforeRegister(DatingParticipant requested, DatingEvent event, User user) {
        confirmedUser(user, requested);
        confirmedEvent(event, requested);
    }

    private void confirmedUser(User user, DatingParticipant participation) {
        participationVerificationService.verifyUser(user, participation);
    }

    private void confirmedEvent(DatingEvent event, DatingParticipant participation) {
        participationVerificationService.verifyDatingEvent(event, participation);
    }

    private DatingParticipant createParticipationRequest(DatingEvent event, User user) {
        return DatingParticipant.request(event, user);
    }

    private DatingParticipant confirmParticipation(DatingParticipant participation, DatingEvent event, User user) {
        DatingParticipant confirmed = participation.confirm(event, user);

        user.addEventFrom(confirmed);
        event.addParticipation(confirmed);

        return confirmed;
    }
}
