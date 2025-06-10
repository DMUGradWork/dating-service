package com.grewmeet.datingservice.domain.dating;

import com.grewmeet.datingservice.domain.user.User;
import com.grewmeet.datingservice.domain.vote.Vote;
import com.grewmeet.datingservice.exception.InvalidDatingEventConfigException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "host_user_id")
    private User hostUser;

    private String location;
    private LocalDateTime eventDateTime;
    private Long maxMaleParticipantsCount;
    private Long maxFemaleParticipantsCount;

    @Column(length = 1000) // 최대 1000자
    private String description;

    @OneToMany(mappedBy = "datingEvent")
    private final List<DatingParticipant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "datingEvent")
    private final List<Vote> votes = new ArrayList<>();

    private DatingEvent(User hostUser,
                         String title,
                         String location,
                         LocalDateTime eventDateTime,
                         Long maxMaleParticipantsCount,
                         Long maxFemaleParticipantsCount,
                         String description) {
        this.hostUser = hostUser;
        this.title = title;
        this.location = location;
        this.eventDateTime = eventDateTime;
        this.maxMaleParticipantsCount = maxMaleParticipantsCount;
        this.maxFemaleParticipantsCount = maxFemaleParticipantsCount;
        this.description = description;
    }

    public static DatingEvent from(User hostUser,
                                    String title,
                                    String location,
                                    LocalDateTime eventDateTime,
                                    Long maxMaleParticipantsCount,
                                    Long maxFemaleParticipantsCount,
                                    String description) {
        return new DatingEvent(
                hostUser,
                title,
                location,
                eventDateTime,
                maxMaleParticipantsCount,
                maxFemaleParticipantsCount,
                description);
    }

    //==연관관계 메서드==//

    public void addParticipation(DatingParticipant participation) {
        if(!canAcceptParticipation(participation)) {
            throw new InvalidDatingEventConfigException("already accepted participation");
        }

        if(participation.isRequestedFromMale()) {
            if(isMaleFull()) {
                throw new InvalidDatingEventConfigException("Male participation cannot be requested from male participation");
            }
        }

        if (participation.isRequestedFromFemale()) {
            if(isFemaleFull()) {
                throw new InvalidDatingEventConfigException("Female participation cannot be requested from female participation");
            }
        }

        participants.add(participation);
    }

    public boolean canAcceptParticipation(DatingParticipant participation) {
        return !isDuplicateRequestOf(participation) && !isGenderFull(participation);
    }

    private boolean isGenderFull(DatingParticipant participation) {
        if (participation.isRequestedFromMale()) {
            return isMaleFull();
        }
        if (participation.isRequestedFromFemale()) {
            return isFemaleFull();
        }
        return false;
    }

    public boolean isFull() {
        return isMaleFull() || isFemaleFull();
    }

    public boolean isMaleFull() {
        return getMaleParticipantsCount() >= maxMaleParticipantsCount;
    }

    public boolean isFemaleFull() {
        return getFemaleParticipantsCount() >= maxFemaleParticipantsCount;
    }

    public Long getMaleParticipantsCount() {
        return participants.stream()
                .filter(DatingParticipant::isRequestedFromMale)
                .count();
    }

    public Long getFemaleParticipantsCount() {
        return participants.stream()
                .filter(DatingParticipant::isRequestedFromFemale)
                .count();
    }

    public boolean isDuplicateRequestOf(DatingParticipant participation) {
        User requestUser = participation.getUser();

        return participants.stream()
                .anyMatch(p -> p.getUser().equals(requestUser));
    }

    public void updateEventInfo(String title,
                                String location,
                                LocalDateTime eventDateTime,
                                Long maxMaleParticipantsCount,
                                Long maxFemaleParticipantsCount,
                                String description) {

        validateUpdateParameters(maxMaleParticipantsCount, maxFemaleParticipantsCount);

        this.title = title;
        this.location = location;
        this.eventDateTime = eventDateTime;
        this.maxMaleParticipantsCount = maxMaleParticipantsCount;
        this.maxFemaleParticipantsCount = maxFemaleParticipantsCount;
        this.description = description;
    }


    private void validateUpdateParameters(Long maxMaleParticipantsCount, Long maxFemaleParticipantsCount) {

        if (maxMaleParticipantsCount < getMaleParticipantsCount()) {
            throw new InvalidDatingEventConfigException(
                    "남성 최대 참가자 수는 현재 남성 참가자 수보다 많아야 합니다.");
        }

        if (maxFemaleParticipantsCount < getFemaleParticipantsCount()) {
            throw new InvalidDatingEventConfigException(
                    "여성 최대 참가자 수는 현재 여성 참가자 수보다 많아야 합니다.");
        }
    }

    public void removeParticipation(DatingParticipant participant) {
        participants.remove(participant);
    }

    public String getHostName() {
        return hostUser.getUsername();
    }

    public void addNewVote(Vote vote) {
        votes.add(vote);
    }
}
