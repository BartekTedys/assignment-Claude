package com.example.assignment_Claude.daos;

import com.example.assignment_Claude.entities.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, Long> {
    List<Household> findByNameContainingIgnoreCase(String name);

    @Query("SELECT h FROM Household h LEFT JOIN FETCH h.pets")
    List<Household> findAllWithPets();
}