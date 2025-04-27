package com.grewmeet.datingservice.repository;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import com.grewmeet.datingservice.domain.dating.DatingParticipant;
import com.grewmeet.datingservice.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<DatingParticipant, Long> {
    Optional<DatingParticipant> findByUserAndDatingEvent(User user, DatingEvent event);
    void deleteAllByDatingEvent_Id(Long eventId);
}
