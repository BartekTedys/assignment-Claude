package com.example.assignment_Claude.dtos;


import java.util.List;

public record HouseholdDto(
        Long id,
        String name,
        String address,
        List<Long> petIds
) {}