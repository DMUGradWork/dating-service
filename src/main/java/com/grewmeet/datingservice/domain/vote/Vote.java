package com.grewmeet.datingservice.domain.vote;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long datingEventId;

    private String title;

    private boolean isClosed = false;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoteOption> options = new ArrayList<>();

    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoteParticipant> participants = new ArrayList<>();

    public void participate(Long userId, Long optionId) {
        ensureVoteIsOpen();
        ensureUserHasNotVoted(userId);
        VoteOption selected = findOption(optionId);
        registerParticipant(userId, selected);
    }

    public void concludeVoting() {
        this.isClosed = true;
    }

    private void ensureVoteIsOpen() {
        if (isClosed) {
            throw new IllegalStateException("이미 마감된 투표입니다.");
        }
    }

    private void ensureUserHasNotVoted(Long userId) {
        boolean alreadyVoted = participants.stream()
                .anyMatch(p -> p.getUserId().equals(userId));

        if (alreadyVoted) {
            throw new IllegalStateException("이미 투표에 참여하셨습니다.");
        }
    }

    private VoteOption findOption(Long optionId) {
        return options.stream()
                .filter(option -> option.getId().equals(optionId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 선택지입니다."));
    }

    private void registerParticipant(Long userId, VoteOption selectedOption) {
        participants.add(new VoteParticipant(this, userId, selectedOption));
    }
}
