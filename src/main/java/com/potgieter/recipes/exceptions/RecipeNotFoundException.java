package com.potgieter.recipes.exceptions;

public class RecipeNotFoundException extends Exception {

    public RecipeNotFoundException(String id) {
        super("Could not find Recipe with id " + id);
    }

    public RecipeNotFoundException() {
    }
}
