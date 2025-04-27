package com.grewmeet.datingservice.service.dating;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import com.grewmeet.datingservice.dto.event.DatingEventResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface DatingEventQueryService {
    List<DatingEventResponse> findAllResponses();
    DatingEventResponse findByEventId(Long eventId);
    List<DatingEventResponse> findAllEventsManagedBy(Long hostId);
}
