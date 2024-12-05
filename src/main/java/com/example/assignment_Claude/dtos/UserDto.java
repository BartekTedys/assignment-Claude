package com.example.assignment_Claude.dtos;

import java.util.Set;

public record UserDto(
        Long id,
        String username,
        Set<String> roles
) {}