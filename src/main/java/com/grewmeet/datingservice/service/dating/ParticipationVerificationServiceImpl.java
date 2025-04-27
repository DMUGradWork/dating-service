package com.grewmeet.datingservice.service.dating;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import com.grewmeet.datingservice.domain.dating.DatingParticipant;
import com.grewmeet.datingservice.domain.user.User;
import org.springframework.stereotype.Service;

@Service
public class ParticipationVerificationServiceImpl implements ParticipationVerificationService {

    @Override
    public void verifyUser(User user, DatingParticipant participation) {
        verifyParticipationsLimitOf(user);
        verifyAlreadyRequested(user, participation);
    }

    @Override
    public void verifyDatingEvent(DatingEvent datingEvent, DatingParticipant participation) {
        verifyParticipationsLimitOf(datingEvent);
        verifyAlreadyRequested(datingEvent, participation);
    }


    private void verifyParticipationsLimitOf(User user) {
        if(user.isParticipationLimitReached()) {
            throw new IllegalStateException("[ERROR] 회원 [" + user.getId() + "]는 동시에 "+ user.participantsCount() +" 개 보다 많은 이벤트에 참여할 수 없습니다. ");
        }
    }

    private void verifyParticipationsLimitOf(DatingEvent datingEvent) {
        if(datingEvent.isFull()) {
            throw new IllegalStateException("[ERROR] 이벤트 [" + datingEvent.getTitle() + "] 은 참여가 마감되었습니다.");
        }
    }

    private void verifyAlreadyRequested(User user , DatingParticipant participation) {
        if(user.hasAlreadyRequested(participation)) {
            throw new IllegalStateException("[ERROR] 회원 [" + user.getId() + "]는 이미" +participation.getDatingEvent().getTitle() + "이벤트에 참여중입니다.");
        }
    }

    private void verifyAlreadyRequested(DatingEvent datingEvent, DatingParticipant participation) {
        if(datingEvent.isDuplicateRequestOf(participation)) {
            throw new IllegalStateException("[ERROR] 이벤트 [" + datingEvent.getTitle() + "] 는 이미 참여중입니다.");
        }
    }
}


