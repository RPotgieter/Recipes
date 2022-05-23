package com.potgieter.recipes.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@Document("recipe")
public class Recipe {

    Recipe(){}

    @Id
    @JsonProperty
    String id;
    @CreatedDate
    @JsonFormat(pattern = "dd‐MM‐yyyy HH:mm", timezone = "UTC")
    @JsonProperty
    Instant createdOn;
    @JsonProperty
    String name;
    boolean vegetarian;
    int servesAmount;
    List<String> ingredients;
    String instructions;
}
