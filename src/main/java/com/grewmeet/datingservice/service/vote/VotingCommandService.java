package com.grewmeet.datingservice.service.vote;

import com.grewmeet.datingservice.dto.vote.VoteParticipationRequestDTO;
import com.grewmeet.datingservice.dto.vote.VoteParticipationResponseDTO;

public interface VotingCommandService {
    VoteParticipationResponseDTO participate(VoteParticipationRequestDTO request);
}
