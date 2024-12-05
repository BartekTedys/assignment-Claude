package com.example.assignment_Claude.services;

import com.example.assignment_Claude.daos.HouseholdRepository;
import com.example.assignment_Claude.daos.PetRepository;
import com.example.assignment_Claude.dtos.HouseholdDto;
import com.example.assignment_Claude.entities.Household;
import com.example.assignment_Claude.entities.Pet;
import com.example.assignment_Claude.services.Exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HouseholdService {
    private final HouseholdRepository householdRepository;
    private final PetRepository petRepository;

    @Transactional
    public HouseholdDto createHousehold(HouseholdDto householdDto) {
        Household household = new Household();
        household.setName(householdDto.name());
        household.setAddress(householdDto.address());

        Household savedHousehold = householdRepository.save(household);
        return convertToDto(savedHousehold);
    }

    @Transactional(readOnly = true)
    public List<HouseholdDto> getAllHouseholds() {
        return householdRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public HouseholdDto getHouseholdById(Long id) {
        Household household = householdRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Household not found with id: " + id));
        return convertToDto(household);
    }

    @Transactional
    public void deleteHousehold(Long id) {
        Household household = householdRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Household not found with id: " + id));

        // Remove associations with pets
        household.getPets().forEach(pet -> pet.setHousehold(null));
        petRepository.saveAll(household.getPets());

        householdRepository.delete(household);
    }

    @Transactional
    public HouseholdDto updateHousehold(Long id, HouseholdDto householdDto) {
        Household existingHousehold = householdRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Household not found"));

        existingHousehold.setName(householdDto.name());
        existingHousehold.setAddress(householdDto.address());

        return convertToDto(householdRepository.save(existingHousehold));
    }

    private HouseholdDto convertToDto(Household household) {
        return new HouseholdDto(
                household.getId(),
                household.getName(),
                household.getAddress(),
                household.getPets().stream().map(Pet::getId).collect(Collectors.toList())
        );
    }
}
