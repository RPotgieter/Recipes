package com.potgieter.recipes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.potgieter.recipes.exceptions.RecipeControllerAdvice;
import com.potgieter.recipes.exceptions.RecipeNotFoundException;
import com.potgieter.recipes.models.Recipe;
import com.potgieter.recipes.repository.RecipeRepository;
import com.potgieter.recipes.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
class RecipeApiV2ControllerTest {

    private MockMvc mvc;

    @Mock
    private RecipeService service;

    @InjectMocks
    private RecipeApiController apiController;

    @Mock
    private RecipeRepository repository;

    private JacksonTester<Recipe> jsonRecipe;
    private JacksonTester<List<Recipe>> jsonListRecipe;


    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(apiController)
                .setControllerAdvice(new RecipeControllerAdvice())
                .build();
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(apiController).isNotNull();
    }

    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
        // given
        given(service.getRecipe("1234"))
                .willReturn(Recipe.builder()
                        .id("1234")
                        .name("test cake")
                        .servesAmount(23)
                        .build()
                );

        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/api/v2/recipes/1234")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonRecipe.write(Recipe.builder()
                        .id("1234")
                        .name("test cake")
                        .servesAmount(23)
                        .build()).getJson()
        );
    }

    @Test
    public void canRetrieveAllRecipes() throws Exception {
        // given
        List<Recipe> allRecipes = new ArrayList<>();
        allRecipes.add(Recipe.builder()
                .id("1")
                .name("test cake 1")
                .servesAmount(1)
                .build());
        allRecipes.add(Recipe.builder()
                .id("2")
                .name("test cake 2")
                .servesAmount(2)
                .build());
        allRecipes.add(Recipe.builder()
                .id("3")
                .name("test cake 3")
                .servesAmount(3)
                .build());
        given(service.getAllRecipes())
                .willReturn(allRecipes);

        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/api/v2/recipes")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonListRecipe.write(allRecipes).getJson()
        );
    }

    @Test
    public void canRetrieveByIdWhenDoesNotExist() throws Exception {
        // given
        given(service.getRecipe("2345"))
                .willThrow(new RecipeNotFoundException());

        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/api/v2/recipes/2345")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    public void canCreateANewRecipe() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                post("/api/v2/recipes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                jsonRecipe.write(Recipe.builder()
                                        .name("test create cake")
                                        .servesAmount(23)
                                        .ingredients(List.of("ing 1, ing 2"))
                                        .instructions("This is just for testing")
                                        .build()).getJson()
                        )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void cannotUpdateARecipeNotFound() throws Exception {
        // given
        given(service.getRecipe("4567"))
                .willThrow(new RecipeNotFoundException());

        // when
        MockHttpServletResponse response = mvc.perform(
                patch("/api/v2/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                jsonRecipe.write(Recipe.builder()
                                        .id("4567")
                                        .name("test updated cake")
                                        .vegetarian(false)
                                        .servesAmount(234)
                                        .ingredients(List.of("ing 1, ing 2", "ing 3"))
                                        .instructions("Updated text")
                                        .build()).getJson()
                        )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }
}