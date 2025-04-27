package com.grewmeet.datingservice.service.vote;

import com.grewmeet.datingservice.domain.vote.Vote;
import com.grewmeet.datingservice.domain.vote.VoteOption;
import com.grewmeet.datingservice.dto.vote.VoteParticipationRequestDTO;
import com.grewmeet.datingservice.dto.vote.VoteParticipationResponseDTO;
import com.grewmeet.datingservice.repository.VoteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VotingCommandServiceImpl implements VotingCommandService {

    private final VoteRepository voteRepository;

    @Override
    public VoteParticipationResponseDTO participate(VoteParticipationRequestDTO request) {
        Vote vote = getVoteOrThrow(request.voteId());

        vote.participate(request.userId(), request.selectedOptionId());

        VoteOption selectedOption = extractSelectedOptionFrom(vote, request.selectedOptionId());

        return buildResponseDTO(request, vote, selectedOption);
    }

    private Vote getVoteOrThrow(Long voteId) {
        return voteRepository.findById(voteId)
                .orElseThrow(() -> new EntityNotFoundException("투표를 찾을 수 없습니다."));
    }

    private VoteOption extractSelectedOptionFrom(Vote vote, Long optionId) {
        return vote.getOptions().stream()
                .filter(opt -> opt.getId().equals(optionId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("선택한 옵션이 유효하지 않습니다."));
    }

    private VoteParticipationResponseDTO buildResponseDTO(VoteParticipationRequestDTO request, Vote vote, VoteOption selected) {
        return new VoteParticipationResponseDTO(
                vote.getId(),
                request.userId(),
                selected.getId(),
                selected.getOptionValue(),
                "투표가 정상적으로 처리되었습니다."
        );
    }
}
