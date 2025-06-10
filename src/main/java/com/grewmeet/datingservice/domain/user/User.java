package com.grewmeet.datingservice.domain.user;

import com.grewmeet.datingservice.domain.dating.DatingParticipant;
import com.grewmeet.datingservice.domain.user.role.Gender;
import com.grewmeet.datingservice.domain.user.role.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    private final static long MAX_PARTICIPATION_COUNT = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private LocalDateTime accessAt;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "user")
    private final List<DatingParticipant> participants = new ArrayList<>();

    private User(Long id, String username) {
        this.id = id;
        this.username = username;
        this.accessAt = LocalDateTime.now();
    }

    public static User create(Long id, String username) {
        return new User(id,username);
    }

    /**
     *기록 서비스 개발 및 연동 작업이 진행되기 전까지
     * 더미 데이터 및 테스트 용도로 사용 예정
     * Setter 처럼 사용하고 추후 삭제 예정
     */
    public void assignRole(UserRole role) {
        this.role = role;
    }


    public int participantsCount() {
        return participants.size();
    }

    public void addEventFrom(DatingParticipant participation){
        participants.add(participation);
    }

    public boolean canRequest(DatingParticipant participation) {
        return !this.isParticipationLimitReached() && !this.hasAlreadyRequested(participation);
    }

    public boolean isParticipationLimitReached() {
        return this.participants.size() >= MAX_PARTICIPATION_COUNT;
    }

    public boolean hasAlreadyRequested(DatingParticipant participation) {
        return participants.stream()
                .anyMatch(p -> p.getDatingEvent().equals(participation.getDatingEvent()));
    }

    public void removeParticipation(DatingParticipant participant) {
        participants.remove(participant);
    }

    public Boolean isMale() {
        return gender.equals(Gender.MALE);
    }

    public Boolean isFeMale() {
        return gender.equals(Gender.FEMALE);
    }
}
