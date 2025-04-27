package com.grewmeet.datingservice.service.dating;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import com.grewmeet.datingservice.dto.event.DatingEventResponse;
import com.grewmeet.datingservice.repository.DatingEventRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatingEventQueryServiceImpl implements DatingEventQueryService {

    private final DatingEventRepository datingEventRepository;

    @Override
    public List<DatingEventResponse> findAllResponses() {
        return datingEventRepository.findAll().stream()
                .map(DatingEventResponse::from)
                .toList();
    }

    @Override
    public DatingEventResponse findByEventId(Long eventId) {
        DatingEvent findEvent =  datingEventRepository.findById(eventId)
                .orElseThrow(IllegalStateException::new);
        return DatingEventResponse.from(findEvent);
    }

    @Override
    public List<DatingEventResponse> findAllEventsManagedBy(Long hostId) {
        return datingEventRepository.findAllByHostUser_Id(hostId).stream()
                .map(DatingEventResponse::from)
                .toList();
    }

}
