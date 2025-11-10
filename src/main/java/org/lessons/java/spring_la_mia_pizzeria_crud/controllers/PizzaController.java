package org.lessons.java.spring_la_mia_pizzeria_crud.controllers;

import java.util.Optional;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.SpecialOffer;
import org.lessons.java.spring_la_mia_pizzeria_crud.service.IngredientService;
import org.lessons.java.spring_la_mia_pizzeria_crud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;
    @Autowired
    private IngredientService ingredientService;


    @GetMapping("/")
    public String index(Model model, Authentication authentication) {

        model.addAttribute("list", pizzaService.findAll());
        model.addAttribute("username", authentication.getName());
        
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model){
        Optional<Pizza> pizzOptional = pizzaService.FindById(id);

        Pizza pizza = pizzOptional.get();

        model.addAttribute("pizza", pizza);

        return "pizzas/show";
    }

    @GetMapping("/searchByName")
    public String searchByName(@RequestParam(name="name") String name, Model model){

        model.addAttribute("list", pizzaService.findByName(name));

        
        return "pizzas/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredients", ingredientService.findAll());
        model.addAttribute("edit", false);
        return "pizzas/create-edit";
    }

    @PostMapping("/create")
    public String strore(@Valid @ModelAttribute("pizza") Pizza formPizza,
    BindingResult bindingResult, Model model ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientService.findAll());

            return"pizzas/create-edit";
        }
        pizzaService.create(formPizza);
        return "redirect:/pizzas/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id,  Model model) {
        model.addAttribute("pizza", pizzaService.FindById(id));
        model.addAttribute("ingredients", ingredientService.findAll());
        model.addAttribute("edit", true);
        return "pizzas/create-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza,
    BindingResult bindingResult, Model model ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientService.findAll());
            return"pizzas/create-edit";
        }
        pizzaService.update(formPizza);
        return "redirect:/pizzas/";
    }
    
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        pizzaService.delete(id);
        return "redirect:/pizzas/";
    }

    @GetMapping("/{id}/specialoffer")
    public String specialoffer(@PathVariable Integer id, Model model) {
        SpecialOffer specialOffer = new SpecialOffer();
        // specialOffer.setPizza(pizzaService.FindById(id));

        model.addAttribute("specialOffer", specialOffer);
        model.addAttribute("edit", false);
        return "specialoffers/create-edit";
    }
    
    
}
