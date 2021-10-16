package com.example.backendshelter.controller.request;

import com.example.backendshelter.model.ShelterCity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShelterCreateRequest {
    private ShelterCity city;
    private String name;
    private int maxPopulation;
}
