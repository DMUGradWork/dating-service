package com.grewmeet.datingservice.service.user;

import com.grewmeet.datingservice.domain.user.User;
import com.grewmeet.datingservice.dto.event.DatingEventSummaryDto;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserQueryService {
    User getUserById(Long id);
//    List<DatingEventSummaryDto> getParticipantsEvents(Long userId);
}
