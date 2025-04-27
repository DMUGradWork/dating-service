package com.grewmeet.datingservice.controller;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import com.grewmeet.datingservice.domain.vote.Vote;
import com.grewmeet.datingservice.dto.event.DatingEventResponse;
import com.grewmeet.datingservice.dto.vote.VoteParticipationRequestDTO;
import com.grewmeet.datingservice.dto.vote.VoteParticipationResponseDTO;
import com.grewmeet.datingservice.service.dating.DatingEventParticipantService;
import com.grewmeet.datingservice.service.vote.DatingEventVoteService;
import com.grewmeet.datingservice.service.vote.VotingCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guests/{userId}")
public class DatingGuestController {

    private final DatingEventParticipantService datingEventParticipationService;
    private final VotingCommandService votingCommandService;

    @PatchMapping("/dating-events/{eventId}/join")
    public ResponseEntity<DatingEventResponse> joinDatingEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        DatingEventResponse joined = datingEventParticipationService.joinAt(eventId, userId);
        return ResponseEntity.accepted().body(joined);
    }

    @PatchMapping("/dating-events/{eventId}/leave")
    public ResponseEntity<Void> leaveDatingEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        datingEventParticipationService.leaveAt(eventId, userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/dating-events/{eventId}/votes/{voteId}/participate")
    public ResponseEntity<VoteParticipationResponseDTO> voteDatingEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long voteId,
            @RequestParam Long optionId) {
        VoteParticipationResponseDTO response = votingCommandService.participate(
                        new VoteParticipationRequestDTO(voteId, userId, optionId));
        return ResponseEntity.ok(response);
    }

}
