package com.clicks.fulafiacampuselectionsystem.dto;

import java.util.List;

public record ClientDto(
        List<UserDto> members,
        List<ElectionDto> elections,
        List<ElectionPositionDto> electionPositions,
        long ussdCode,
        String address,
        String name,
        long owner
) {
}
