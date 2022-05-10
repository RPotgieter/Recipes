package com.potgieter.recipes;

import com.potgieter.recipes.models.Recipe;
import com.potgieter.recipes.repository.RecipeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
                .name("Test cake")
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
    public void shouldHaveCorrectDateFormat() {
//      Given
        Instant now = Instant.parse("2019-10-01T08:25:24.00Z");

        Recipe updated = Recipe.builder()
                .id(toSave.getId())
                .createdOn(now)
                .build();

//      When:
        repository.save(updated);

//      Then:
        repository.findById("1234").ifPresent(data -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd‐MM‐yyyy HH:mm");
            String formattedDate = simpleDateFormat.format(Date.from(now));
            assertThat("01‐10‐2019 10:25").isEqualTo(formattedDate);
        });
    }

    @Test
    public void shouldUpdateNameAfterEdit() {
//      Given
        Recipe updated = Recipe.builder()
                .id(toSave.getId())
                .createdOn(toSave.getCreatedOn())
                .name("Vanilla Cake")
                .ingredients(toSave.getIngredients())
                .instructions(toSave.getInstructions())
                .servesAmount(toSave.getServesAmount())
                .vegetarian(toSave.isVegetarian())
                .build();

//      When:
        repository.save(updated);

//      Then:
        repository.findById("1234").ifPresent(data -> {
            assertThat(data.getName()).isEqualTo("Vanilla Cake");
        });
    }

    @Test
    public void shouldUpdateIngredientsAfterEdit() {
//      Given
        Recipe updated = Recipe.builder()
                .id(toSave.getId())
                .createdOn(toSave.getCreatedOn())
                .ingredients(List.of("Ingredient 3", "Ingredient 4"))
                .instructions(toSave.getInstructions())
                .servesAmount(toSave.getServesAmount())
                .vegetarian(toSave.isVegetarian())
                .build();

//      When:
        repository.save(updated);

//      Then:
        repository.findById("1234").ifPresent(data -> {
            assertThat(data.getIngredients()).isNotEmpty();
            assertThat(data.getIngredients()).contains("Ingredient 3");
            assertThat(data.getIngredients()).contains("Ingredient 4");
        });
    }

    @Test
    public void shouldUpdateInstructionsAfterEdit() {
//      Given
        Recipe updated = Recipe.builder()
                .id(toSave.getId())
                .createdOn(toSave.getCreatedOn())
                .ingredients(toSave.getIngredients())
                .instructions("Stir counter clockwise")
                .servesAmount(toSave.getServesAmount())
                .vegetarian(toSave.isVegetarian())
                .build();

//      When:
        repository.save(updated);

//      Then:
        repository.findById("1234").ifPresent(data -> {
            assertThat(data.getInstructions()).isEqualTo("Stir counter clockwise");
        });
    }

    @Test
    public void shouldUpdateServesAmountAfterEdit() {
//      Given
        Recipe updated = Recipe.builder()
                .id(toSave.getId())
                .createdOn(toSave.getCreatedOn())
                .ingredients(toSave.getIngredients())
                .instructions(toSave.getInstructions())
                .servesAmount(10)
                .vegetarian(toSave.isVegetarian())
                .build();

//      When:
        repository.save(updated);

//      Then:
        repository.findById("1234").ifPresent(data -> {
            assertThat(data.getServesAmount()).isEqualTo(10);
        });
    }

    @Test
    public void shouldUpdateIsVegetarianAfterEdit() {
//      Given
        Recipe updated = Recipe.builder()
                .id(toSave.getId())
                .createdOn(toSave.getCreatedOn())
                .ingredients(toSave.getIngredients())
                .instructions(toSave.getInstructions())
                .servesAmount(toSave.getServesAmount())
                .vegetarian(false)
                .build();

//      When:
        repository.save(updated);

//      Then:
        repository.findById("1234").ifPresent(data -> {
            assertThat(data.isVegetarian()).isEqualTo(false);
        });
    }
}
