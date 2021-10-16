package com.example.backendshelter.controller.response;

import com.example.backendshelter.model.ShelterCity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShelterReturnResponse {
    private Long id;
    private ShelterCity city;
    private String name;
    private int maxPopulation;
    private List<PetReturnResponse> petsRet;
}
