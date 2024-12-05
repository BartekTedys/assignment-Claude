package com.example.assignment_Claude.controllers;

import com.example.assignment_Claude.dtos.HouseholdDto;
import com.example.assignment_Claude.services.HouseholdService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HouseholdGraphQLController {
    private final HouseholdService householdService;

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<HouseholdDto> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public HouseholdDto getHouseholdById(@Argument Long id) {
        return householdService.getHouseholdById(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public HouseholdDto createHousehold(
            @Argument String name,
            @Argument String address
    ) {
        HouseholdDto householdDto = new HouseholdDto(null, name, address, null);
        return householdService.createHousehold(householdDto);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public HouseholdDto updateHousehold(
            @Argument Long id,
            @Argument String name,
            @Argument String address
    ) {
        HouseholdDto householdDto = new HouseholdDto(null, name, address, null);
        return householdService.updateHousehold(id, householdDto);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteHousehold(@Argument Long id) {
        householdService.deleteHousehold(id);
        return true;
    }
}