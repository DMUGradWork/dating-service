package com.grewmeet.datingservice.service.vote;

import com.grewmeet.datingservice.domain.vote.Vote;
import com.grewmeet.datingservice.dto.vote.CreateVoteRequestDto;
import com.grewmeet.datingservice.dto.vote.VoteResponseDto;
import jakarta.validation.Valid;
import java.util.List;

public interface DatingEventVoteService {
    VoteResponseDto createVote(Long eventId, @Valid CreateVoteRequestDto request);
    List<VoteResponseDto> findAllVotesByEventId(Long eventId);
    VoteResponseDto findVoteById(Long voteId);
    void closeVote(Long voteId);
    void deleteVote(Long voteId);
}
