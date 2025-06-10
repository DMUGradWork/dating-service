package com.grewmeet.datingservice.dto.vote;

import java.util.List;

public record VoteResponseDto(
        Long voteId,
        String title,
        boolean isClosed,
        List<VoteOptionResponse> options,
        int totalParticipants
) {
    public record VoteOptionResponse(
            Long optionId,
            String optionValue,
            int voteCount
    ) {}
}