package com.example.assignment_Claude.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Household Entity
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "households")
public class Household {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Household name is required")
    @Column(nullable = false)
    private String name;

    @Size(max = 500, message = "Address cannot exceed 500 characters")
    private String address;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets = new ArrayList<>();
}