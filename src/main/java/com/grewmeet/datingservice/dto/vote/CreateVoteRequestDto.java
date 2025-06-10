package com.grewmeet.datingservice.dto.vote;

import java.util.List;

public record CreateVoteRequestDto(String title,
                                   List<String> options) {
}
