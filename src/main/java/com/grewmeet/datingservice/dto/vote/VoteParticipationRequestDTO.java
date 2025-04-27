package com.grewmeet.datingservice.dto.vote;

public record VoteParticipationRequestDTO(Long voteId,
                                          Long userId,
                                          Long selectedOptionId) {}
