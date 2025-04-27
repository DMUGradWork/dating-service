package com.grewmeet.datingservice.service.dating;

import com.grewmeet.datingservice.dto.event.DatingEventResponse;

public interface DatingEventParticipantService {
    DatingEventResponse joinAt(Long eventId, Long userId);
    void leaveAt(Long eventId, Long userId);
}
