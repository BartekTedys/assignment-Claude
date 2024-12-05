package com.example.assignment_Claude.controllers;

import com.example.assignment_Claude.dtos.PetDto;
import com.example.assignment_Claude.entities.PetType;
import com.example.assignment_Claude.services.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class PetGraphQLController {
    private final PetService petService;

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<PetDto> getAllPets() {
        return petService.getAllPets();
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public PetDto getPetById(@Argument Long id) {
        return petService.getPetById(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PetDto createPet(
            @Argument String name,
            @Argument PetType type,
            @Argument String birthDate,
            @Argument Long householdId
    ) {
        PetDto petDto = new PetDto(null, name, type, birthDate, householdId);
        return petService.createPet(petDto);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PetDto updatePet(
            @Argument Long id,
            @Argument String name,
            @Argument PetType type,
            @Argument String birthDate,
            @Argument Long householdId
    ) {
        PetDto petDto = new PetDto(null, name, type, birthDate, householdId);
        return petService.updatePet(id, petDto);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deletePet(@Argument Long id) {
        petService.deletePet(id);
        return true;
    }
}