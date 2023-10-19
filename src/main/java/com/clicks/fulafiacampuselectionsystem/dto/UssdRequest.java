package com.clicks.fulafiacampuselectionsystem.dto;

public record UssdRequest(
        String sessionId,
        String phoneNumber,
        String networkCode,
        String serviceCode,
        String text
) {
}
