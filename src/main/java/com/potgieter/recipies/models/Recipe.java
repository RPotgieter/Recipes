package com.potgieter.recipies.models;

import lombok.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Value
@Document(collation = "recipe")
public class Recipe {

    @Id
    String id;
    @CreatedDate
    Instant createdOn;
    boolean vegetarian;
    int servesAmount;
    String ingredients;
    String instructions;
}
