package com.potgieter.recipies;

import com.potgieter.recipies.models.Recipe;
import com.potgieter.recipies.repository.RecipeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RecipeApplicationTests {

    @Autowired
    RecipeRepository repository;

    Recipe toSave;

    @BeforeEach
    public void setUp() {
        //      Given:
        toSave = Recipe.builder()
                .id("1234")
                .createdOn(Instant.now())
                .ingredients(Arrays.asList("Test ingredient 1", "Test ingredient 2"))
                .instructions("Stir regularly")
                .servesAmount(5)
                .vegetarian(true)
                .build();
    }

    @AfterEach
    public void after() {
        repository.deleteById("1234");
    }

    @Test
    public void shouldBeNotEmptyAfterSave() {
        //      When:
        repository.save(toSave);

//      Then:
        assertThat(repository.findAll()).isNotEmpty();
        assertThat(repository.findById("1234")).isNotEmpty();

    }

    @Test
    public void shouldBeEmptyAfterDelete() {
//      When:
        repository.deleteById("1234");

//      Then:
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void shouldBeUpdatedAfterEdit() {
//      Given (part2)
        Recipe updated = Recipe.builder()
                .id(toSave.getId())
                .createdOn(toSave.getCreatedOn())
                .ingredients(toSave.getIngredients())
                .instructions(toSave.getInstructions())
                .servesAmount(10)
                .vegetarian(false)
                .build();

//      When:
        repository.save(updated);

//      Then:
        repository.findById("1234").ifPresent(data -> {
            assertThat(data.getServesAmount()).isEqualTo(10);
            assertThat(data.isVegetarian()).isEqualTo(false);
        });
    }
}
