package com.grewmeet.datingservice.service.dating;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import com.grewmeet.datingservice.dto.event.DatingEventRegistrationRequestDTO;

public interface DatingEventRegistrationService {
    DatingEvent registerEventBy(Long hostId,DatingEventRegistrationRequestDTO request);
}
