package com.grewmeet.datingservice.controller;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import com.grewmeet.datingservice.dto.event.DatingEventCardDto;
import com.grewmeet.datingservice.dto.event.DatingEventRegistrationRequestDTO;
import com.grewmeet.datingservice.dto.event.DatingEventResponseNew;
import com.grewmeet.datingservice.dto.event.DatingEventUpdateRequestDTO;
import com.grewmeet.datingservice.dto.vote.CreateVoteRequestDto;
import com.grewmeet.datingservice.dto.vote.VoteResponseDto;
import com.grewmeet.datingservice.service.dating.DatingEventCommandService;
import com.grewmeet.datingservice.service.dating.DatingEventQueryService;
import com.grewmeet.datingservice.service.dating.DatingEventRegistrationService;
import com.grewmeet.datingservice.service.vote.DatingEventVoteService;
import com.grewmeet.datingservice.util.UriUtils;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hosts/{hostId}")
public class DatingHostController {

    private final DatingEventRegistrationService datingEventRegistrationService;
    private final DatingEventCommandService datingEventCommandService;
    private final DatingEventQueryService datingEventQueryService;
    private final DatingEventVoteService datingEventVoteService;

    @GetMapping("/dating-events")
    public ResponseEntity<List<DatingEventCardDto>> retrieveAllDatingEventsManagedByHost(
            @PathVariable Long hostId) {
        List<DatingEventCardDto> events = datingEventQueryService.findAllEventsManagedBy(hostId);

        return ResponseEntity.ok(events);
    }

    @PostMapping("/dating-events")
    private ResponseEntity<DatingEvent> createEvent(
            @PathVariable Long hostId,
            @Valid @RequestBody DatingEventRegistrationRequestDTO request) {
        DatingEvent registeredEvent = datingEventRegistrationService.registerEventBy(hostId,request);
        URI location = UriUtils.buildLocationUri(registeredEvent.getId());

        return ResponseEntity.created(location).body(registeredEvent);
    }

    @GetMapping("/dating-events/{eventId}")
    public ResponseEntity<DatingEventResponseNew> retrieveDatingEventById(
            @PathVariable Long hostId,
            @PathVariable Long eventId) {
        return ResponseEntity.ok(datingEventQueryService.findByEventId(eventId));
    }

    @PostMapping("/dating-events/{eventId}/announcement")
    private ResponseEntity<String> publishAnnouncementAtTop(
            @PathVariable Long hostId,
            @PathVariable Long eventId,
            @RequestBody String announcementContent) {
        return ResponseEntity.ok("Announcement published for event: " + eventId);
    }

    @PatchMapping("/dating-events/{eventId}")
    public ResponseEntity<DatingEventResponseNew> updateDatingEventConfig(
            @PathVariable Long hostId,
            @PathVariable Long eventId,
            @RequestBody DatingEventUpdateRequestDTO request) {
        datingEventCommandService.updateWith(eventId,request);
        DatingEventResponseNew updated = datingEventQueryService.findByEventId(eventId);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/dating-events/{eventId}")
    public ResponseEntity<Void> deleteDatingEventById(
            @PathVariable Long hostId,
            @PathVariable Long eventId) {
        datingEventCommandService.deleteTo(eventId);

        return ResponseEntity.noContent().build();
    }

    // 투표 생성
    @PostMapping("/dating-events/{eventId}/votes")
    public ResponseEntity<VoteResponseDto> createVote(
            @PathVariable Long hostId,
            @PathVariable Long eventId,
            @Valid @RequestBody CreateVoteRequestDto request) {
        VoteResponseDto vote = datingEventVoteService.createVote(eventId, request);
        URI location = UriUtils.buildLocationUri(vote.voteId());

        return ResponseEntity.created(location).body(vote);
    }

    // 특정 이벤트의 모든 투표 조회
    @GetMapping("/dating-events/{eventId}/votes")
    public ResponseEntity<List<VoteResponseDto>> getAllVotes(
            @PathVariable Long hostId,
            @PathVariable Long eventId) {
        List<VoteResponseDto> votes = datingEventVoteService.findAllVotesByEventId(eventId);

        return ResponseEntity.ok(votes);
    }

    // 특정 투표 상세 조회
    @GetMapping("/dating-events/{eventId}/votes/{voteId}")
    public ResponseEntity<VoteResponseDto> getVoteDetail(
            @PathVariable Long hostId,
            @PathVariable Long eventId,
            @PathVariable Long voteId) {
        VoteResponseDto vote = datingEventVoteService.findVoteById(voteId);

        return ResponseEntity.ok(vote);
    }

    // 투표 마감
    @PatchMapping("/dating-events/{eventId}/votes/{voteId}/close")
    public ResponseEntity<Void> closeVote(
            @PathVariable Long hostId,
            @PathVariable Long eventId,
            @PathVariable Long voteId) {
        datingEventVoteService.closeVote(voteId);

        return ResponseEntity.noContent().build();
    }

    // 투표 삭제 (필요하다면)
    @DeleteMapping("/dating-events/{eventId}/votes/{voteId}")
    public ResponseEntity<Void> deleteVote(
            @PathVariable Long hostId,
            @PathVariable Long eventId,
            @PathVariable Long voteId) {
        datingEventVoteService.deleteVote(voteId);

        return ResponseEntity.noContent().build();
    }

}
