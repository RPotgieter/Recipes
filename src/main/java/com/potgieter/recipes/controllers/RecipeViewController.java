package com.potgieter.recipes.controllers;

import com.potgieter.recipes.exceptions.RecipeNotFoundException;
import com.potgieter.recipes.models.Recipe;
import com.potgieter.recipes.services.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RecipeViewController {

    Logger _logger = LoggerFactory.getLogger(RecipeViewController.class);

    private final RecipeService recipeService;

    public RecipeViewController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping(path = "/recipes/{id}")
    public ModelAndView getRecipe(@PathVariable String id) throws RecipeNotFoundException  {
        ModelAndView view = new ModelAndView("recipe");
        Recipe recipe = recipeService.getRecipe(id);
        view.addObject("recipe", recipe);
        return view;
    }

    @GetMapping("/recipes")
    public ModelAndView getAllRecipes() {
        ModelAndView view = new ModelAndView("recipes");
        List<Recipe> recipes = recipeService.getAllRecipes();
        _logger.debug("All Recipes" + recipes);
        view.addObject("data", recipes);
        return view;
    }

    @GetMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable String id) throws RecipeNotFoundException {
        recipeService.removeRecipe(id);
        return "redirect:/recipes";
    }

    @GetMapping ("/update/{id}")
    public ModelAndView updateRecipe(@PathVariable String id) throws RecipeNotFoundException {
        ModelAndView view = new ModelAndView("update");
        Recipe find = recipeService.getRecipe(id);
        view.addObject("find", find);
        return view;
    }

    @PostMapping("/recipes/update")
    public String updateRecipe(@ModelAttribute Recipe update) throws RecipeNotFoundException {
        _logger.debug("Updating with payload: " + update);
        recipeService.updateRecipe(update);
        return "redirect:/recipes";
    }

    @GetMapping("/recipes/create")
    public ModelAndView createRecipeView() {
        return new ModelAndView("create", "create", Recipe.builder().build());
    }

    @PostMapping("/recipes/create")
    public String createRecipeAction(@ModelAttribute Recipe create) {
        _logger.debug("Creating with payload: " + create);
        recipeService.createRecipe(create);
        return "redirect:/recipes";
    }

}
