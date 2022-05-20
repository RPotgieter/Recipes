package com.potgieter.recipes.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.NonNull;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Value
@Builder
@Document("recipe")
public class Recipe {

    @Id
    String id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @CreatedDate
    @JsonFormat(pattern = "dd‐MM‐yyyy HH:mm", timezone = "UTC")
    Instant createdOn;
    String name;
    boolean vegetarian;
    int servesAmount;
    List<String> ingredients;
    String instructions;
}
