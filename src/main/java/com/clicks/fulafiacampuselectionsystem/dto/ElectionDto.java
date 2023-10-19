package com.clicks.fulafiacampuselectionsystem.dto;

import java.util.List;

public record ElectionDto(
        String createTime,
        String title,
        String startDate,
        String startTime,
        String stopDate,
        String stopTime,
        List<CandidateDto> candidates,
        long owner,
        long ussdCode
) {
}
