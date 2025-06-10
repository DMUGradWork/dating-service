package com.grewmeet.datingservice.dto.event;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import java.time.LocalDateTime;

public record DatingEventResponseNew(Long id,
                                     String title,
                                     String hostname,
                                     LocalDateTime eventDate,
                                     String location,
                                     Long currentMaleParticipants,
                                     Long maxMaleParticipantsCount,
                                     Long currentFemaleParticipants,
                                     Long maxFemaleParticipantsCount,
                                     String description) {

    public static DatingEventResponseNew from(DatingEvent datingEvent) {
        return new DatingEventResponseNew(
                datingEvent.getId(),
                datingEvent.getTitle(),
                datingEvent.getHostName(),
                datingEvent.getEventDateTime(),
                datingEvent.getLocation(),
                datingEvent.getMaleParticipantsCount(),
                datingEvent.getMaxMaleParticipantsCount(),
                datingEvent.getFemaleParticipantsCount(),
                datingEvent.getMaxFemaleParticipantsCount(),
                datingEvent.getDescription());
    }
}
