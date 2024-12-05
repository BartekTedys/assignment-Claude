package com.example.assignment_Claude.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

// Pet Entity
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private PetType type;

    private String birthDate;

    @ManyToOne
    @JoinColumn(name = "household_id")
    private Household household;
}