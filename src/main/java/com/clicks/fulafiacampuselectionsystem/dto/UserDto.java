package com.clicks.fulafiacampuselectionsystem.dto;

public record UserDto(
        long id,
        String name,
        String identity,
        String email,
        String phone,
        String role
) {
}
