package com.example.assignment_Claude.dtos;

import com.example.assignment_Claude.entities.PetType;

import java.time.LocalDate;

public record PetDto(
        Long id,
        String name,
        PetType type,
        String birthDate,
        Long householdId
) {}