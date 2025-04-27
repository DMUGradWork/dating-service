package com.grewmeet.datingservice.controller;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import com.grewmeet.datingservice.domain.user.User;
import com.grewmeet.datingservice.dto.event.DatingEventResponse;
import com.grewmeet.datingservice.dto.user.UserRegistrationRequestDTO;
import com.grewmeet.datingservice.exception.ServiceUsageCriteriaNotMetException;
import com.grewmeet.datingservice.exception.UserNotFoundException;
import com.grewmeet.datingservice.service.dating.DatingEventQueryService;
import com.grewmeet.datingservice.service.user.UserQualificationService;
import com.grewmeet.datingservice.service.user.UserQueryService;
import com.grewmeet.datingservice.service.user.UserQueryServiceImpl;
import com.grewmeet.datingservice.service.user.UserRegistrationService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dating")
public class DatingController {

    private final UserQueryService userQueryService;
    private final DatingEventQueryService datingEventQueryService;
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

//    //필요성 검증 필요
//    @GetMapping("/{userId}")
//    public ResponseEntity<User> retrieveUser(@PathVariable Long userId) {
//        return ResponseEntity.ok(userQueryService.getUserById(userId));
//    }

    @GetMapping("/dating-events")
    public List<DatingEventResponse> retrieveAllDatingEvent() {
        return datingEventQueryService.findAllResponses();
    }

//    @PostMapping("/dating-events")
//    public ResponseEntity<Void> createDatingEvent(@RequestParam Long userId) {
//        URI redirectUri = UriComponentsBuilder.fromPath("/hosts/{hostId}/dating-events")
//                .buildAndExpand(userId)
//                .toUri();
//
//        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(redirectUri).build();
//    }

    @GetMapping("/dating-events/{eventId}")
    public ResponseEntity<DatingEventResponse> retrieveDatingEventById(
            @PathVariable Long eventId) {
        return ResponseEntity.ok(datingEventQueryService.findByEventId(eventId));
    }

    @PostMapping("/dating-events/{eventId}")
    public ResponseEntity<?> forwardToJoinEvent(
            @PathVariable Long eventId,
            @RequestParam Long userId) {
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/guest/{userId}/dating-events/{eventId}/join")
                .buildAndExpand(userId, eventId)
                .toUri();

        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(uri).build();
    }

}
