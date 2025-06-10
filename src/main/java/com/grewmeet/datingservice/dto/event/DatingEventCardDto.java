package com.grewmeet.datingservice.dto.event;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import java.time.LocalDateTime;

public record DatingEventCardDto(Long id,
                                 String title,
                                 String hostname,
                                 LocalDateTime eventDate,
                                 String location,
                                 Long currentMaleParticipants,
                                 Long maxMaleParticipantsCount,
                                 Long currentFemaleParticipants,
                                 Long maxFemaleParticipantsCount) {

    public static DatingEventCardDto from(DatingEvent datingEvent) {
        return new DatingEventCardDto(
                datingEvent.getId(),
                datingEvent.getTitle(),
                datingEvent.getHostName(),
                datingEvent.getEventDateTime(),
                datingEvent.getLocation(),
                datingEvent.getMaleParticipantsCount(),
                datingEvent.getMaxMaleParticipantsCount(),
                datingEvent.getFemaleParticipantsCount(),
                datingEvent.getMaxFemaleParticipantsCount());
    }
}