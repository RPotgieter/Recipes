package com.potgieter.recipies.repository;


import com.potgieter.recipies.models.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "recipe", path = "recipe")
public interface RecipeRepository extends MongoRepository<Recipe, String> {
}
