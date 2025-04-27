package com.grewmeet.datingservice.service.vote;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import com.grewmeet.datingservice.domain.vote.Vote;
import org.springframework.stereotype.Service;

@Service
public class DatingEventVoteServiceImpl implements DatingEventVoteService {



    @Override
    public void concludeTo(Long voteId) {

    }

    @Override
    public Vote createVoteAt(Long datingEventId) {
        return null;
    }
}
