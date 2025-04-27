package com.grewmeet.datingservice.controller;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import com.grewmeet.datingservice.domain.vote.Vote;
import com.grewmeet.datingservice.dto.event.DatingEventRegistrationRequestDTO;
import com.grewmeet.datingservice.dto.event.DatingEventResponse;
import com.grewmeet.datingservice.dto.event.DatingEventUpdateRequestDTO;
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
//
//    @PostMapping("/users")
//    public ResponseEntity<User> createUser(@Valid @RequestBody UserRegistrationRequestDTO request) {
//        User savedUser = userRegistrationService.register(request);
//
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(savedUser.getId())
//                .toUri();
//
//        return ResponseEntity.created(location).body(savedUser);
//    }

    @PostMapping("/dating-events")
    private ResponseEntity<DatingEvent> createEvent(
            @PathVariable Long hostId,
            @Valid @RequestBody DatingEventRegistrationRequestDTO request) {
        DatingEvent registeredEvent = datingEventRegistrationService.registerEventBy(hostId,request);
        URI location = UriUtils.buildLocationUri(registeredEvent.getId());

        return ResponseEntity.created(location).body(registeredEvent);
    }

    @GetMapping("/dating-events")
    public ResponseEntity<List<DatingEventResponse>> retrieveAllDatingEventsManagedByHost(
            @PathVariable Long hostId) {
        List<DatingEventResponse> events = datingEventQueryService.findAllEventsManagedBy(hostId);

        return ResponseEntity.ok(events);
    }

    @GetMapping("/dating-events/{eventId}")
    public ResponseEntity<DatingEventResponse> retrieveDatingEventById(
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
    public ResponseEntity<DatingEventResponse> updateDatingEventConfig(
            @PathVariable Long hostId,
            @PathVariable Long eventId,
            @RequestBody DatingEventUpdateRequestDTO request) {
        datingEventCommandService.updateWith(eventId,request);
        DatingEventResponse updated = datingEventQueryService.findByEventId(eventId);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/dating-events/{eventId}")
    public ResponseEntity<Void> deleteDatingEventById(
            @PathVariable Long hostId,
            @PathVariable Long eventId) {
        datingEventCommandService.deleteTo(eventId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/dating-events/{eventId}/votes")
    public ResponseEntity<Vote> openVote(
            @PathVariable Long hostId,
            @PathVariable Long eventId) {
        Vote voteAt = datingEventVoteService.createVoteAt(eventId);

        return ResponseEntity.ok(voteAt);
    }

    private ResponseEntity<Void> concludeVote(
            @PathVariable Long hostId,
            @PathVariable Long eventId,
            @PathVariable Long voteId) {
        datingEventVoteService.concludeTo(voteId);
        return ResponseEntity.noContent().build();
    }

}
