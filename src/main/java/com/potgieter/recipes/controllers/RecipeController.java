package com.potgieter.recipes.controllers;

import com.potgieter.recipes.exceptions.RecipeNotFoundException;
import com.potgieter.recipes.models.Recipe;
import com.potgieter.recipes.services.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v2")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {

        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}")
    public Recipe getRecipe(@PathVariable String id) throws RecipeNotFoundException  {
        return recipeService.getRecipe(id);
    }

    @GetMapping("/recipe")
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @DeleteMapping("/recipe/{id}")
    void deleteRecipe(@PathVariable String id) throws RecipeNotFoundException {
        recipeService.removeRecipe(id);
    }

    @PatchMapping("/recipe")
    Recipe updateRecipe(@RequestBody Recipe updateBody) throws RecipeNotFoundException {
        return recipeService.updateRecipe(updateBody);
    }

    @PutMapping("/recipe")
    Recipe createRecipe(@RequestBody Recipe recipe) {
        return recipeService.createRecipe(recipe);
    }
}
