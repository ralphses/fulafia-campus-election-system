package com.clicks.fulafiacampuselectionsystem.dto;

import java.util.List;

public record ElectionResultDto(
        ElectionDto election,
        int totalVotes,
        List<ElectionWinnerDto> winners
) {
}
