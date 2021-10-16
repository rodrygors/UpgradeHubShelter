package com.example.backendshelter.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated
    private ShelterCity city;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Min(0)
    private int maxPopulation;

    @OneToMany(mappedBy ="shelter")
    private List<Pet> pets;
}