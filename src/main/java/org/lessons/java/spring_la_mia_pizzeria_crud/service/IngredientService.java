package org.lessons.java.spring_la_mia_pizzeria_crud.service;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Ingredient;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredient> findAll(){
        return ingredientRepository.findAll();
    }

    public Ingredient findById(Integer id){
        return ingredientRepository.findById(id).get();
    }

    public List<Ingredient> findByName(String name){
        return ingredientRepository.findByNameContaining(name);
    }

    public Ingredient create(@Valid Ingredient ingredient){
        return ingredientRepository.save(ingredient);
    }

    public Ingredient update(@Valid Ingredient ingredient){
        return ingredientRepository.save(ingredient);
    }

    public void delete(Integer id){
        Ingredient ingredientToDelete = findById(id);

        for (Pizza linkedPizza : ingredientToDelete.getPizzas()) {
            linkedPizza.getIngredients().remove(ingredientToDelete);
        }
        
        ingredientRepository.delete(ingredientToDelete);
    }
}
