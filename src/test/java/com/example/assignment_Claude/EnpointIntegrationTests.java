package com.example.assignment_Claude;

import com.example.assignment_Claude.dtos.PetDto;
import com.example.assignment_Claude.entities.PetType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureGraphQlTester
@Transactional // This will rollback transactions after each test
class EndpointIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    @WithMockUser(roles = "USER")
    void getAllPets_WithUserRole_ShouldSucceed() throws Exception {
        mockMvc.perform(get("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void graphql_GetAllPets_WithAdminRole_ShouldReturnPetsWithCorrectStructure() {
        String query = """
            query {
                getAllPets {
                    id
                    name
                    type
                }
            }
            """;

        graphQlTester.document(query)
                .execute()
                .path("getAllPets")
                .entityList(PetDto.class)
                .satisfies(pets -> {
                    // Verify we have pets
                    assertFalse(pets.isEmpty(), "Pet list should not be empty");

                    // Check structure of first pet
                    PetDto firstPet = pets.get(0);
                    assertNotNull(firstPet.id(), "Pet ID should not be null");
                    assertNotNull(firstPet.name(), "Pet name should not be null");
                    assertNotNull(firstPet.type(), "Pet type should not be null");
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void graphql_CreatePet_WithAdminRole_ShouldCreateAndReturnNewPet() {
        String mutation = """
            mutation {
                createPet(
                    name: "TestDog",
                    type: DOG,
                    birthDate: "2020-01-01",
                    householdId: "1"
                ) {
                    id
                    name
                    type
                    birthDate
                }
            }
            """;

        graphQlTester.document(mutation)
                .execute()
                .path("createPet")
                .entity(PetDto.class)
                .satisfies(pet -> {
                    assertNotNull(pet.id(), "Created pet should have an ID");
                    assertEquals("TestDog", pet.name(), "Pet name should match input");
                    assertEquals(PetType.DOG, pet.type(), "Pet type should match input");
                    assertEquals("2020-01-01", pet.birthDate(), "Pet birthDate should match input");
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void graphql_GetPetById_WithAdminRole_ShouldReturnCorrectPet() {
        String query = """
            query {
                getPetById(id: "1") {
                    id
                    name
                    type
                }
            }
            """;

        graphQlTester.document(query)
                .execute()
                .path("getPetById")
                .entity(PetDto.class)
                .satisfies(pet -> {
                    assertEquals(1L, pet.id(), "Should retrieve pet with ID 1");
                    assertEquals("Buddy", pet.name(), "Should retrieve pet named Buddy");
                    assertEquals(PetType.DOG, pet.type(), "Should be a dog");
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void graphql_DeletePet_WithAdminRole_ShouldSucceed() {
        // First create a pet to delete
        String createMutation = """
            mutation {
                createPet(
                    name: "PetToDelete",
                    type: DOG,
                    birthDate: "2020-01-01",
                    householdId: "1"
                ) {
                    id
                }
            }
            """;

        String petId = graphQlTester.document(createMutation)
                .execute()
                .path("createPet.id")
                .entity(String.class)
                .get();

        // Then delete it
        String deleteMutation = String.format("""
            mutation {
                deletePet(id: "%s")
            }
            """, petId);

        graphQlTester.document(deleteMutation)
                .execute()
                .path("deletePet")
                .entity(Boolean.class)
                .satisfies(result -> assertTrue(result, "Delete operation should return true"));
    }
}