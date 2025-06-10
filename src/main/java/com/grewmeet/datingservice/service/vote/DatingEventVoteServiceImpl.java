package com.grewmeet.datingservice.service.vote;

import com.grewmeet.datingservice.domain.dating.DatingEvent;
import com.grewmeet.datingservice.domain.vote.Vote;
import com.grewmeet.datingservice.domain.vote.VoteOption;
import com.grewmeet.datingservice.dto.vote.CreateVoteRequestDto;
import com.grewmeet.datingservice.dto.vote.VoteResponseDto;
import com.grewmeet.datingservice.repository.DatingEventRepository;
import com.grewmeet.datingservice.repository.VoteRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DatingEventVoteServiceImpl implements DatingEventVoteService {

    private final DatingEventRepository datingEventRepository;
    private final VoteRepository voteRepository;

    @Override
    public VoteResponseDto createVote(Long eventId, CreateVoteRequestDto request) {
        DatingEvent datingEvent = findDatingEventById(eventId);

        // Vote 엔티티 생성 (리플렉션으로 직접 생성하거나 팩토리 메서드 필요)
        Vote vote = createNewVote(eventId, request.title());
        Vote savedVote = voteRepository.save(vote);

        // VoteOption들 생성 - Vote의 options 리스트에 자동으로 추가됨 (cascade)
        request.options().forEach(optionValue -> {
            VoteOption option = new VoteOption(optionValue, savedVote);
            savedVote.getOptions().add(option);
        });

        // DatingEvent에 투표 추가
        datingEvent.addNewVote(savedVote);

        return convertToVoteResponse(savedVote);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoteResponseDto> findAllVotesByEventId(Long eventId) {
        findDatingEventById(eventId); // 이벤트 존재 확인

        List<Vote> votes = voteRepository.findByDatingEventId(eventId);

        return votes.stream()
                .map(this::convertToVoteResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VoteResponseDto findVoteById(Long voteId) {
        Vote vote = findVoteByIdInternal(voteId);
        return convertToVoteResponse(vote);
    }

    @Override
    public void closeVote(Long voteId) {
        Vote vote = findVoteByIdInternal(voteId);
        vote.concludeVoting();
    }

    @Override
    public void deleteVote(Long voteId) {
        Vote vote = findVoteByIdInternal(voteId);

        if (!vote.getParticipants().isEmpty()) {
            throw new IllegalStateException("참여자가 있는 투표는 삭제할 수 없습니다.");
        }

        voteRepository.delete(vote);
    }

    // === Private Helper Methods ===

    private Vote createNewVote(Long eventId, String title) {
        // Vote 엔티티에 생성자나 팩토리 메서드 필요
        // 임시로 리플렉션 사용하거나 Vote에 정적 팩토리 메서드 추가 필요
        try {
            Vote vote = new Vote();
            // private 필드 설정을 위한 리플렉션 또는 Vote에 생성 메서드 추가 필요
            return vote;
        } catch (Exception e) {
            throw new RuntimeException("Vote 생성 실패", e);
        }
    }

    // === Private Helper Methods ===

    private DatingEvent findDatingEventById(Long eventId) {
        return datingEventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("데이팅 이벤트를 찾을 수 없습니다. ID: " + eventId));
    }

    private Vote findVoteByIdInternal(Long voteId) {
        return voteRepository.findById(voteId)
                .orElseThrow(() -> new EntityNotFoundException("투표를 찾을 수 없습니다. ID: " + voteId));
    }

    private VoteResponseDto convertToVoteResponse(Vote vote) {
        List<VoteResponseDto.VoteOptionResponse> optionResponses = vote.getOptions().stream()
                .map(option -> new VoteResponseDto.VoteOptionResponse(
                        option.getId(),
                        option.getOptionValue(),
                        getVoteCountForOption(vote, option.getId())
                ))
                .toList();

        return new VoteResponseDto(
                vote.getId(),
                vote.getTitle(),
                vote.isClosed(),
                optionResponses,
                vote.getParticipants().size()
        );
    }

    private int getVoteCountForOption(Vote vote, Long optionId) {
        return (int) vote.getParticipants().stream()
                .filter(participant -> participant.getSelectedOption().getId().equals(optionId))
                .count();
    }
}