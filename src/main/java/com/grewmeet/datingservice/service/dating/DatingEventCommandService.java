package com.grewmeet.datingservice.service.dating;

import com.grewmeet.datingservice.dto.event.DatingEventUpdateRequestDTO;

public interface DatingEventCommandService {
    void updateWith(Long eventId, DatingEventUpdateRequestDTO request);
    void deleteTo(Long eventId);
}
