package com.grewmeet.datingservice.service.dating;

import com.grewmeet.datingservice.dto.event.DatingEventResponseNew;

public interface DatingEventParticipantService {
    DatingEventResponseNew joinAt(Long eventId, Long userId);
    void leaveAt(Long eventId, Long userId);
}
