package com.grewmeet.datingservice.repository;

import com.grewmeet.datingservice.domain.vote.Vote;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByDatingEventId(Long eventId);
}
