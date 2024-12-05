package com.example.assignment_Claude;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTests {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithAnonymousUser
    void accessProtectedEndpoint_AsAnonymous_ShouldBeDenied() throws Exception {
        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void accessProtectedEndpoint_AsUser_ShouldBeAllowed() throws Exception {
        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/households"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void accessAdminEndpoint_AsUser_ShouldBeDenied() throws Exception {
        mockMvc.perform(get("/api/admin/stats"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void accessProtectedEndpoints_AsAdmin_ShouldBeAllowed() throws Exception {
        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/households"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "userpass", roles = "USER")
    void graphqlQuery_WithAuth_ShouldSucceed() throws Exception {
        String query = """
            {"query": "query { getAllPets { id name type } }"}
            """;

        mockMvc.perform(post("/graphql")
                        .content(query)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void graphqlQuery_WithoutAuth_ShouldBeAllowed() throws Exception {
        String query = """
            {"query": "query { getAllPets { id name type } }"}
            """;

        mockMvc.perform(post("/graphql")
                        .content(query)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}