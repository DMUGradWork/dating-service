package com.grewmeet.datingservice.service.dating;

import com.grewmeet.datingservice.dto.event.DatingEventCardDto;
import com.grewmeet.datingservice.dto.event.DatingEventResponseNew;
import java.util.List;

public interface DatingEventQueryService {
    List<DatingEventCardDto> findAllResponses();
    DatingEventResponseNew findByEventId(Long eventId);
    List<DatingEventCardDto> findAllEventsManagedBy(Long hostId);
    List<DatingEventCardDto> findAllEventParticipantsAtUserId(Long userId);
}
