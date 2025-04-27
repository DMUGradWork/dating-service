package com.grewmeet.datingservice.service.dating;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import com.grewmeet.datingservice.dto.event.DatingEventUpdateRequestDTO;
import com.grewmeet.datingservice.repository.DatingEventRepository;
import com.grewmeet.datingservice.repository.ParticipantRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatingEventCommandServiceImpl implements DatingEventCommandService {

    private final DatingEventRepository datingEventRepository;
    private final ParticipantRepository participantRepository;

    @Override
    public void updateWith(Long eventId, DatingEventUpdateRequestDTO request) {
        DatingEvent event = getEventById(eventId);
        event.updateConfig(request.title(), request.maxParticipants());
        datingEventRepository.save(event);
    }

    private DatingEvent getEventById(Long eventId) {
        return datingEventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with ID: " + eventId));
    }

    @Override
    @Transactional
    public void deleteTo(Long eventId) {
        participantRepository.deleteAllByDatingEvent_Id(eventId);
        DatingEvent event = getEventById(eventId);
        datingEventRepository.delete(event);
    }
}
