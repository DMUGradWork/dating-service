package com.grewmeet.datingservice.dto.event;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import java.time.LocalDateTime;
import java.util.List;

public record DatingEventResponse(Long id,
                                  String title,
                                  Long maxParticipants,
                                  LocalDateTime eventDate,
                                  List<ParticipantSimple> participants
) {

    public static DatingEventResponse from(DatingEvent event) {
        List<ParticipantSimple> participantList = event.getParticipants()
                .stream()
                .map(p -> new ParticipantSimple(
                        p.getUser().getId(),
                        p.getUser().getUsername()
                ))
                .toList();

        return new DatingEventResponse(
                event.getId(),
                event.getTitle(),
                event.getMaxParticipants(),
                event.getEventDate(),
                participantList
        );
    }

    public record ParticipantSimple(
            Long userId,
            String username
    ) {}
}

