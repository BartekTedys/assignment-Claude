package com.example.assignment_Claude.services;


import com.example.assignment_Claude.daos.HouseholdRepository;
import com.example.assignment_Claude.daos.PetRepository;
import com.example.assignment_Claude.dtos.PetDto;
import com.example.assignment_Claude.entities.Household;
import com.example.assignment_Claude.entities.Pet;
import com.example.assignment_Claude.services.Exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final HouseholdRepository householdRepository;

    @Transactional(readOnly = true)
    public List<PetDto> getAllPets() {
        return petRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PetDto getPetById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found with id: " + id));
        return convertToDto(pet);
    }

    @Transactional
    public PetDto updatePet(Long id, PetDto petDto) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found with id: " + id));

        existingPet.setName(petDto.name());
        existingPet.setType(petDto.type());
        existingPet.setBirthDate(petDto.birthDate());

        // Update household if provided
        if (petDto.householdId() != null) {
            Household household = householdRepository.findById(petDto.householdId())
                    .orElseThrow(() -> new EntityNotFoundException("Household not found"));
            existingPet.setHousehold(household);
        }

        return convertToDto(petRepository.save(existingPet));
    }

    @Transactional
    public void deletePet(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found with id: " + id));

        // Remove household association
        if (pet.getHousehold() != null) {
            pet.getHousehold().getPets().remove(pet);
            pet.setHousehold(null);
        }

        petRepository.delete(pet);
    }

    @Transactional
    public PetDto createPet(PetDto petDto) {
        Pet pet = new Pet();
        pet.setName(petDto.name());
        pet.setType(petDto.type());
        pet.setBirthDate(petDto.birthDate());

        // Link to household if provided
        if (petDto.householdId() != null) {
            Household household = householdRepository.findById(petDto.householdId())
                    .orElseThrow(() -> new EntityNotFoundException("Household not found"));
            pet.setHousehold(household);
        }

        Pet savedPet = petRepository.save(pet);
        return convertToDto(savedPet);
    }

    private PetDto convertToDto(Pet pet) {
        return new PetDto(
                pet.getId(),
                pet.getName(),
                pet.getType(),
                pet.getBirthDate(),
                pet.getHousehold() != null ? pet.getHousehold().getId() : null
        );
    }
}
