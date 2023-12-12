package com.clicks.fulafiacampuselectionsystem.dto;

public record UserDto(
        long id,
        String fullName,
        String identity,
        String email,
        String phone,
        String password,
        String role,
        String organization,
        String organizationAddress
) {
}
