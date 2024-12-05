package com.example.assignment_Claude.controllers;

import com.example.assignment_Claude.dtos.HouseholdDto;
import com.example.assignment_Claude.services.HouseholdService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/households")
@RequiredArgsConstructor
public class HouseholdController {
    private final HouseholdService householdService;

    @PostMapping
    public ResponseEntity<HouseholdDto> createHousehold(
            @Valid @RequestBody HouseholdDto householdDto
    ) {
        return ResponseEntity.ok(householdService.createHousehold(householdDto));
    }

    @GetMapping
    public ResponseEntity<List<HouseholdDto>> getAllHouseholds() {
        return ResponseEntity.ok(householdService.getAllHouseholds());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HouseholdDto> updateHousehold(
            @PathVariable Long id,
            @Valid @RequestBody HouseholdDto householdDto
    ) {
        return ResponseEntity.ok(householdService.updateHousehold(id, householdDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHousehold(@PathVariable Long id) {
        householdService.deleteHousehold(id);
        return ResponseEntity.noContent().build();
    }
}