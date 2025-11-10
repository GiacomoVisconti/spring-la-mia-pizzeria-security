package org.lessons.java.spring_la_mia_pizzeria_crud.service;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    public List<Pizza> findAll(){
        return pizzaRepository.findAll(Sort.by("name"));
    }



    public Optional<Pizza> FindById(Integer id){
        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);

        if (pizzaOptional.isEmpty()) {
    
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza non trovata");
        }

        return pizzaOptional;
    }

    public List<Pizza> findByName(String name){
        return pizzaRepository.findByNameContaining(name);
    }

    public Pizza create(Pizza pizza){
        return pizzaRepository.save(pizza);
    }

    public Pizza update(Pizza pizza){
        return pizzaRepository.save(pizza);
    }

    public void delete(Integer id){
        pizzaRepository.deleteById(id);

    }
}
