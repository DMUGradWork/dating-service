package com.grewmeet.datingservice.dto.event;

import java.time.LocalDateTime;

public record DatingEventRegistrationRequestDTO(String title,
                                                String location,
                                                LocalDateTime eventDateTime,
                                                Long maxMaleParticipantsCount,
                                                Long maxFemaleParticipantsCount,
                                                String description) {
}
