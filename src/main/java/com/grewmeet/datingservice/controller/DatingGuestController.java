package com.grewmeet.datingservice.controller;

import com.grewmeet.datingservice.dto.event.DatingEventCardDto;
import com.grewmeet.datingservice.dto.vote.VoteParticipationRequestDTO;
import com.grewmeet.datingservice.dto.vote.VoteParticipationResponseDTO;
import com.grewmeet.datingservice.service.dating.DatingEventParticipantService;
import com.grewmeet.datingservice.service.dating.DatingEventQueryService;
import com.grewmeet.datingservice.service.vote.VotingCommandService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("users/{userId}")
public class DatingGuestController {

    private final DatingEventParticipantService datingEventParticipationService;
    private final DatingEventQueryService datingEventQueryService;
    private final VotingCommandService votingCommandService;

    @GetMapping("/dating-events")
    public ResponseEntity<List<DatingEventCardDto>> getMyDatingEvents(
            @PathVariable("userId") Long userId) {
        List<DatingEventCardDto> allEventParticipantsAtUserId =
                datingEventQueryService.findAllEventParticipantsAtUserId(userId);

        return ResponseEntity.ok(allEventParticipantsAtUserId);
    }

    @PatchMapping("/dating-events/{eventId}/leave")
    public ResponseEntity<Void> leaveDatingEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        datingEventParticipationService.leaveAt(eventId, userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/dating-events/{eventId}/votes/{voteId}")
    public ResponseEntity<VoteParticipationResponseDTO> participateInVote(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long voteId,
            @RequestParam Long optionId) {
        VoteParticipationResponseDTO response = votingCommandService.participate(
                        new VoteParticipationRequestDTO(voteId, userId, optionId));
        return ResponseEntity.ok(response);
    }

}
