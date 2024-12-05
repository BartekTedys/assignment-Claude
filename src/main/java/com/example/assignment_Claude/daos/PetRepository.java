package com.example.assignment_Claude.daos;


import com.example.assignment_Claude.entities.Pet;
import com.example.assignment_Claude.entities.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByType(PetType type);
    List<Pet> findByHousehold_Id(Long householdId);
}
