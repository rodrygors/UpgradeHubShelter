package com.example.backendshelter.controller;

import com.example.backendshelter.controller.request.CreatePetRQ;
import com.example.backendshelter.controller.request.ShelterCreateRequest;
import com.example.backendshelter.controller.response.PetReturnResponse;
import com.example.backendshelter.controller.response.ShelterReturnResponse;
import com.example.backendshelter.model.Pet;
import com.example.backendshelter.model.Shelter;
import com.example.backendshelter.service.ShelterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@Validated
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    //Add shelters
    @PostMapping(value = "/shelters")
    public List<ShelterReturnResponse> addShelters(@RequestBody @Valid List<ShelterCreateRequest> sheltersReqs) {
        List<Shelter> shelters = new ArrayList<>();
        List<ShelterReturnResponse> sheltersResponse = new ArrayList<>();

        //Transform ShelterRequest list into Shelter list
        for (ShelterCreateRequest shelterReq : sheltersReqs) {
            shelters.add(Shelter
                    .builder()
                    .city(shelterReq.getCity())
                    .name(shelterReq.getName())
                    .maxPopulation(shelterReq.getMaxPopulation())
                    .build());
        }
        shelterService.saveShelters(shelters);

        //Transform Shelter list objects into ShelterRequest list
        for (Shelter shelter : shelters) {
            sheltersResponse.add(new ShelterReturnResponse(
                    shelter.getId(),
                    shelter.getCity(),
                    shelter.getName(),
                    shelter.getMaxPopulation(),
                    Collections.emptyList()
            ));
        }

        return sheltersResponse;
    }

    //Add pets to Shelter
    @PostMapping(value = "/shelters/{id}/pet")
    public List<PetReturnResponse> addPetsToShelter(@PathVariable(value = "id") Long id, @RequestBody @Valid List<CreatePetRQ> petsReq) {
        List<Pet> pets = new ArrayList<>();

        //Transform PetRequest list into Pet list
        for (CreatePetRQ petReq : petsReq) {
            pets.add(Pet
                    .builder()
                    .petType(petReq.getPetType())
                    .name(petReq.getName())
                    .build());
        }

        pets = shelterService.addPetsToShelter(id, pets);

        //Transform Pet list into PetRequest list
        List<PetReturnResponse> petsResp = new ArrayList<>();
        for (Pet pet : pets) {
            petsResp.add(new PetReturnResponse(
                    pet.getId(),
                    pet.getPetType()
            ));
        }
        return petsResp;
    }

    //Get shelter by name
    @GetMapping(value = "/shelters/{name}")
    public ShelterReturnResponse getShelterByName(@PathVariable(name = "name")String name){
        Shelter shelter = shelterService.findByName(name);
        //Create ShelterResponse object from the returned shelter, without pet list
        ShelterReturnResponse shelterResponse = (new ShelterReturnResponse(
                shelter.getId(),
                shelter.getCity(),
                shelter.getName(),
                shelter.getMaxPopulation(),
                new ArrayList<>()
        ));

        //Add pet list into ShelterResponse object
        if(!shelter.getPets().isEmpty() && shelter.getPets() != null){
            for(Pet pet : shelter.getPets()){
                shelterResponse.getPetsRet().add(new PetReturnResponse(
                        pet.getId(),
                        pet.getPetType()
                ));
            }
        }

        return shelterResponse;
    }

    @GetMapping(value = "/shelters-exists/{id}")
    public ShelterReturnResponse existsById(@PathVariable(name = "id")Long id){
        Shelter shelter = shelterService.existsById(id);
        //Create ShelterResponse object from the returned shelter, without pet list
        ShelterReturnResponse shelterResponse = (new ShelterReturnResponse(
                shelter.getId(),
                shelter.getCity(),
                shelter.getName(),
                shelter.getMaxPopulation(),
                new ArrayList<>()
                ));

        //Add pet list into ShelterResponse object
        if(!shelter.getPets().isEmpty() && shelter.getPets() != null){
            for(Pet pet : shelter.getPets()){
                shelterResponse.getPetsRet().add(new PetReturnResponse(
                        pet.getId(),
                        pet.getPetType()
                ));
            }
        }

        return shelterResponse;
    }

    @DeleteMapping(value = "/shelter/{id}")
    public void deleteShelterById(@PathVariable(name = "id") Long id){
        shelterService.deleteById(id);
    }
}
