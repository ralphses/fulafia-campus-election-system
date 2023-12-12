package com.clicks.fulafiacampuselectionsystem.dto;

public record CandidateDto (
        String name,
        String phone,
        int voteCount,
        String position,
        long electionId
) {
}
