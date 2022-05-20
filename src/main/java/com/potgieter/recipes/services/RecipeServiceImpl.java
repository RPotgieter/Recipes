package com.potgieter.recipes.services;

import com.potgieter.recipes.exceptions.RecipeNotFoundException;
import com.potgieter.recipes.models.Recipe;
import com.potgieter.recipes.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository repository;

    public RecipeServiceImpl(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe getRecipe(String id) throws RecipeNotFoundException {
        return repository.findById(id).orElseThrow(RecipeNotFoundException::new);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return repository.findAll();
    }

    @Override
    public void removeRecipe(String id) throws RecipeNotFoundException {
        Recipe toDelete = repository.findById(id).orElseThrow(RecipeNotFoundException::new);
        repository.delete(toDelete);
    }

    @Override
    public Recipe updateRecipe(Recipe updateData) throws RecipeNotFoundException {
        repository.findById(updateData.getId()).orElseThrow(RecipeNotFoundException::new);
        return repository.save(updateData);
    }

    @Override
    public Recipe createRecipe(Recipe createData) {
        return repository.insert(createData);
    }
}
