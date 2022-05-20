package com.potgieter.recipes.services;

import com.potgieter.recipes.exceptions.RecipeNotFoundException;
import com.potgieter.recipes.models.Recipe;

import java.util.List;

public interface RecipeService {

    Recipe getRecipe(String id) throws RecipeNotFoundException;
    List<Recipe> getAllRecipes();
    void removeRecipe(String id) throws RecipeNotFoundException;
    Recipe updateRecipe(Recipe updateData) throws RecipeNotFoundException;
    Recipe createRecipe(Recipe createData);
}
