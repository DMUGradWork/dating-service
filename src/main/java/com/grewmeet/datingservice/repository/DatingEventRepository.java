package com.grewmeet.datingservice.repository;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatingEventRepository extends JpaRepository<DatingEvent, Long> {
    List<DatingEvent> findAllByHostUser_Id(Long hostId);
    List<DatingEvent> findAllByParticipantsUser_Id(Long participantsUserId);
    List<DatingEvent> findAllByParticipants_User_Id(Long userId);
}
