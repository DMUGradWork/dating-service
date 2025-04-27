package com.grewmeet.datingservice.domain.dating;

import com.grewmeet.datingservice.domain.user.User;
import com.grewmeet.datingservice.exception.InvalidDatingEventConfigException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class DatingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "dating_event_id")
    private Long id;

    private String title;

    @OneToOne
    @JoinColumn(name = "host_user_id")
    private User hostUser;

    private Long maxParticipants;
    private LocalDateTime eventDate;

    @OneToMany(mappedBy = "datingEvent")
    private final List<DatingParticipant> participants = new ArrayList<>();

    private DatingEvent(User hostUser, String title, Long maxParticipants) {
        this.hostUser = hostUser;
        this.title = title;
        this.maxParticipants = maxParticipants;
    }

    public static DatingEvent from(User hostUser, String title, Long maxParticipants) {
        return new DatingEvent(hostUser, title, maxParticipants);
    }

    //==연관관계 메서드==//

    public void addParticipation(DatingParticipant participation) {
        participants.add(participation);
    }

    public boolean canAcceptFor(DatingParticipant participation) {
        return !this.isFull() && !this.isDuplicateRequestOf(participation);
    }

    public boolean isFull() {
        return participants.size() >= maxParticipants;
    }

    public boolean isDuplicateRequestOf(DatingParticipant participation) {
        return participants.stream()
                .anyMatch(p -> p.getUser().equals(participation.getUser()));
    }

    public void updateConfig(String requestedTitle, Long requestedMaxParticipants) {
        this.title = requestedTitle;
        if(requestedMaxParticipants <= participants.size()) {
            throw new InvalidDatingEventConfigException("최대 인원 수는 현재 참여자보다 많아야 합니다.");
        }
        this.maxParticipants = requestedMaxParticipants;
    }

    public void removeParticipation(DatingParticipant participant) {
        participants.remove(participant);
    }
}
