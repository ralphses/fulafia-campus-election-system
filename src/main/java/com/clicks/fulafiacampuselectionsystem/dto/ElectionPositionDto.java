package com.clicks.fulafiacampuselectionsystem.dto;

public record ElectionPositionDto(
        String requirements,
        String title,
        double fee,
        long clientId
) {
}
