package com.grewmeet.datingservice.domain.dating;

import com.grewmeet.datingservice.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class DatingParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dating_event_id")
    private DatingEvent datingEvent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime participationDateTime;

    private DatingParticipant(DatingEvent datingEvent, User user) {
        this.datingEvent = datingEvent;
        this.user = user;
        this.participationDateTime = LocalDateTime.now();
    }

    private DatingParticipant(DatingEvent datingEvent, User user, LocalDateTime participationDateTime) {
        this.datingEvent = datingEvent;
        this.user = user;
        this.participationDateTime = participationDateTime;
    }

    public static DatingParticipant request(DatingEvent datingEvent, User user) {
        return new DatingParticipant(datingEvent, user);
    }

    public DatingParticipant confirm(DatingEvent datingEvent, User user) {
        return new DatingParticipant(datingEvent, user, this.participationDateTime);
    }
}

