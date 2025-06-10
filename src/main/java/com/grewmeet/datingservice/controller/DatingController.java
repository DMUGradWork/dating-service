package com.grewmeet.datingservice.controller;

import com.grewmeet.datingservice.domain.user.User;
import com.grewmeet.datingservice.dto.event.DatingEventCardDto;
import com.grewmeet.datingservice.dto.event.DatingEventResponseNew;
import com.grewmeet.datingservice.dto.user.UserRegistrationRequestDTO;
import com.grewmeet.datingservice.service.dating.DatingEventParticipantService;
import com.grewmeet.datingservice.service.dating.DatingEventQueryService;
import com.grewmeet.datingservice.service.user.UserRegistrationService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequiredArgsConstructor
@RequestMapping("/dating")
public class DatingController {

    private final DatingEventQueryService datingEventQueryService;
    private final DatingEventParticipantService datingEventParticipationService;
    private final UserRegistrationService userRegistrationService;

//    검증해서 넘어오는 것을 사용할거라 검증부는 별도의 서비스로 처리 예정
//    private final UserQualificationService userQualificationService;
//    public ResponseEntity<User> accessToDatingEvent(UserRegistrationRequestDTO request) {
//        User qualifiedUser = userQualificationService.accessQualifiedTo(request);
//        return ResponseEntity.ok(qualifiedUser);
//    }
//
//    private ResponseEntity<?> accessQualified(UserRegistrationRequestDTO dto) {
//        try {
//          qualificationService.verifyDatingServiceAccessQualifications(dto);
//        } catch (ServiceUsageCriteriaNotMetException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//        return ResponseEntity.ok().build();
//    }

    @PostMapping
    public ResponseEntity<User> getOrRegisterUser(@Valid @RequestBody UserRegistrationRequestDTO request) {
        User savedUser = userRegistrationService.findExistingOrRegisterNew(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedUser);
    }

    @GetMapping("/dating-events")
    public List<DatingEventCardDto> retrieveAllDatingEvent() {
        return datingEventQueryService.findAllResponses();
    }

    @GetMapping("/dating-events/{eventId}")
    public ResponseEntity<DatingEventResponseNew> retrieveDatingEventById(
            @PathVariable Long eventId) {
        return ResponseEntity.ok(datingEventQueryService.findByEventId(eventId));
    }

    @PostMapping("/dating-events/{eventId}/join")
    public ResponseEntity<DatingEventResponseNew> joinEvent(
            @PathVariable Long eventId,
            @RequestParam Long userId) {

        DatingEventResponseNew joined = datingEventParticipationService.joinAt(eventId, userId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(userId)
                .toUri();

        return ResponseEntity.created(location).body(joined);
    }

//    @PatchMapping("/dating-events/{eventId}/join")
//    public ResponseEntity<DatingEventResponse> joinDatingEvent(
//            @PathVariable Long userId,
//            @PathVariable Long eventId) {
//        DatingEventResponse joined = datingEventParticipationService.joinAt(eventId, userId);
//        return ResponseEntity.accepted().body(joined);
//    }

}
