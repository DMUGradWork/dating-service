package com.grewmeet.datingservice.repository;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatingEventRepository extends JpaRepository<DatingEvent, Long> {
    List<DatingEvent> findAllByHostUser_Id(Long hostId);
}
