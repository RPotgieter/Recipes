package com.potgieter.recipes.controllers;

import com.potgieter.recipes.exceptions.RecipeNotFoundException;
import com.potgieter.recipes.models.Recipe;
import com.potgieter.recipes.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class RecipeApiController {

    private final RecipeService recipeService;

    public RecipeApiController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping(value = "/recipes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Recipe getRecipe(@PathVariable String id) throws RecipeNotFoundException  {
        return recipeService.getRecipe(id);
    }

    @GetMapping("/recipes")
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @DeleteMapping("/recipes/{id}")
    void deleteRecipe(@PathVariable String id) throws RecipeNotFoundException {
        recipeService.removeRecipe(id);
    }

    @PatchMapping("/recipes")
    Recipe updateRecipe(@RequestBody Recipe updateBody) throws RecipeNotFoundException {
        Recipe found = recipeService.getRecipe(updateBody.getId());
        found.toBuilder().createdOn(found.getCreatedOn());
        return recipeService.updateRecipe(updateBody);
    }

    @PostMapping("/recipes")
    @ResponseStatus(HttpStatus.CREATED)
    Recipe createRecipe(@RequestBody Recipe recipe) {
        return recipeService.createRecipe(recipe);
    }
}
