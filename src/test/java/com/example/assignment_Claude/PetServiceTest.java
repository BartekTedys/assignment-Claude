package com.example.assignment_Claude;

import com.example.assignment_Claude.daos.HouseholdRepository;
import com.example.assignment_Claude.daos.PetRepository;
import com.example.assignment_Claude.dtos.PetDto;
import com.example.assignment_Claude.entities.Pet;
import com.example.assignment_Claude.entities.PetType;
import com.example.assignment_Claude.services.PetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private HouseholdRepository householdRepository;

    @InjectMocks
    private PetService petService;

    @Test
    void getAllPets_ShouldReturnAllPets() {
        // Arrange
        Pet pet1 = new Pet(1L, "Dog1", PetType.DOG, "2020-01-01", null);
        Pet pet2 = new Pet(2L, "Cat1", PetType.CAT, "2021-01-01", null);
        when(petRepository.findAll()).thenReturn(List.of(pet1, pet2));

        // Act
        List<PetDto> result = petService.getAllPets();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Dog1", result.get(0).name());
        assertEquals("Cat1", result.get(1).name());
    }

    @Test
    void getPetById_WhenPetExists_ShouldReturnPet() {
        // Arrange
        Pet pet = new Pet(1L, "Dog1", PetType.DOG, "2020-01-01", null);
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        // Act
        PetDto result = petService.getPetById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Dog1", result.name());
        assertEquals(PetType.DOG, result.type());
    }

    @Test
    void createPet_ShouldReturnCreatedPet() {
        // Arrange
        PetDto petDto = new PetDto(null, "Dog1", PetType.DOG, "2020-01-01", null);
        Pet pet = new Pet(1L, "Dog1", PetType.DOG, "2020-01-01", null);
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        // Act
        PetDto result = petService.createPet(petDto);

        // Assert
        assertNotNull(result);
        assertEquals("Dog1", result.name());
        assertEquals(PetType.DOG, result.type());
    }
}