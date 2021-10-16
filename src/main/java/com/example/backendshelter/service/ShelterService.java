package com.example.backendshelter.service;

import com.example.backendshelter.model.Pet;
import com.example.backendshelter.model.Shelter;
import com.example.backendshelter.repository.PetRepository;
import com.example.backendshelter.repository.ShelterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShelterService {
    private final ShelterRepository shelterRepo;
    private final PetRepository petRepo;

    public ShelterService(ShelterRepository shelterRepo, PetRepository petRepo) {
        this.shelterRepo = shelterRepo;
        this.petRepo = petRepo;
    }

    public List<Shelter> saveShelters(List<Shelter> shelters) {
        for (Shelter shelter : shelters) {
            shelterRepo.save(shelter);
        }
        return shelters;
    }

    public List<Pet> addPetsToShelter(Long id, List<Pet> pets) {
        Optional<Shelter> shelter = shelterRepo.findById(id);
        List<Pet> returnPets = new ArrayList<>();
        //Check if shelter exists
        if(shelter.isPresent()){
            //Add pets to shelter by adding shelter id in each pet
            for(Pet pet : pets){
                pet.setShelter(shelter.get());
                returnPets.add(petRepo.save(pet));
            }
            return returnPets;
        }
        //Exception
        return null;
    }

    public Shelter findByName(String name) {
        return shelterRepo.findByName(name);
    }

    public Shelter existsById(Long id) {
        if(shelterRepo.existsById(id)){
            return shelterRepo.findById(id).get();
        }
        else return null;
    }

    public void deleteById(Long id) {
        Optional<Shelter> shelter = shelterRepo.findById(id);
        //Check if shelter exists
        if(shelter.isPresent()){
            //Check if there are any pets in the shelter
            if(!shelter.get().getPets().isEmpty()){
                //Delete all pets in the shelter
                for(Pet pet : shelter.get().getPets()){
                    petRepo.deleteById(pet.getId());
                }
            }
            //Delete shelter
            shelterRepo.deleteById(id);
        }
    }
}
