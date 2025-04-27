package com.grewmeet.datingservice.dto.vote;

public record VoteParticipationResponseDTO(
        Long voteId,
        Long userId,
        Long selectedOptionId,
        String selectedOptionValue,
        String message
) {}
