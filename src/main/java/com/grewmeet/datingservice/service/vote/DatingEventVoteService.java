package com.grewmeet.datingservice.service.vote;

import com.grewmeet.datingservice.domain.vote.Vote;

public interface DatingEventVoteService {
    void concludeTo(Long vote);
    Vote createVoteAt(Long datingEventId);
}
