package com.grewmeet.datingservice.service.dating;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import com.grewmeet.datingservice.domain.dating.DatingParticipant;
import com.grewmeet.datingservice.domain.user.User;

public interface ParticipationVerificationService {
    void verifyUser(User user, DatingParticipant participation);
    void verifyDatingEvent(DatingEvent datingEvent, DatingParticipant participation);
}
