package com.grewmeet.datingservice.service.dating;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import com.grewmeet.datingservice.dto.event.DatingEventCardDto;
import com.grewmeet.datingservice.dto.event.DatingEventResponseNew;
import com.grewmeet.datingservice.repository.DatingEventRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatingEventQueryServiceImpl implements DatingEventQueryService {

    private final DatingEventRepository datingEventRepository;

    @Override
    public List<DatingEventCardDto> findAllResponses() {
        return datingEventRepository.findAll().stream()
                .map(DatingEventCardDto::from)
                .toList();
    }

    @Override
    public DatingEventResponseNew findByEventId(Long eventId) {
        DatingEvent findEvent =  datingEventRepository.findById(eventId)
                .orElseThrow(IllegalStateException::new);
        return DatingEventResponseNew.from(findEvent);
    }

    @Override
    public List<DatingEventCardDto> findAllEventsManagedBy(Long hostId) {
        return datingEventRepository.findAllByHostUser_Id(hostId).stream()
                .map(DatingEventCardDto::from)
                .toList();
    }

    @Override
    public List<DatingEventCardDto> findAllEventParticipantsAtUserId(Long userId) {
        return datingEventRepository.findAllByParticipants_User_Id(userId)
                .stream()
                .map(DatingEventCardDto::from).
                toList();
    }
}
