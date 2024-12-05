package com.example.assignment_Claude.controllers;


import com.example.assignment_Claude.dtos.PetDto;
import com.example.assignment_Claude.services.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @PostMapping
    public ResponseEntity<PetDto> createPet(
            @Valid @RequestBody PetDto petDto
    ) {
        return ResponseEntity.ok(petService.createPet(petDto));
    }

    @GetMapping
    public ResponseEntity<List<PetDto>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDto> updatePet(
            @PathVariable Long id,
            @Valid @RequestBody PetDto petDto
    ) {
        return ResponseEntity.ok(petService.updatePet(id, petDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
}