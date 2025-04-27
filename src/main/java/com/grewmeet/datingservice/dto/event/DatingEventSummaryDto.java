package com.grewmeet.datingservice.dto.event;

import java.time.LocalDateTime;

public record DatingEventSummaryDto(Long id, String title, LocalDateTime date) {
}
