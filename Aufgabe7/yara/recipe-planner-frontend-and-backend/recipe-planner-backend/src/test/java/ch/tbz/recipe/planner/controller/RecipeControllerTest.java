package ch.tbz.recipe.planner.controller;

import ch.tbz.recipe.planner.domain.Recipe;
import ch.tbz.recipe.planner.repository.RecipeRepository;
import ch.tbz.recipe.planner.service.RecipeService;
import ch.tbz.recipe.planner.mapper.RecipeEntityMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecipeController.class)
@MockBean(RecipeRepository.class)
class RecipeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RecipeService service;

    @MockBean
    RecipeEntityMapper mapper;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldReturnList() throws Exception {
        UUID id = UUID.randomUUID();
        Recipe recipe = new Recipe(id, "Test", "Desc", "img", List.of());

        Mockito.when(service.getRecipes()).thenReturn(List.of(recipe));

        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].name").value("Test"))
                .andExpect(jsonPath("$[0].description").value("Desc"))
                .andExpect(jsonPath("$[0].imageUrl").value("img"))
                .andExpect(jsonPath("$[0].ingredients").isArray());
    }

    @Test
    void shouldReturnRecipeById() throws Exception {
        UUID id = UUID.randomUUID();
        Recipe recipe = new Recipe(id, "Test", "Desc", "img", List.of());

        Mockito.when(service.getRecipeById(id)).thenReturn(recipe);

        mockMvc.perform(get("/api/recipes/recipe/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.description").value("Desc"))
                .andExpect(jsonPath("$.imageUrl").value("img"))
                .andExpect(jsonPath("$.ingredients").isArray());
    }

    @Test
    void shouldAddRecipe() throws Exception {
        UUID id = UUID.randomUUID();
        Recipe create = new Recipe(
                null,
                "New",
                "Desc",
                "img",
                List.of()
        );

        Recipe created = new Recipe(
                id,
                "New",
                "Desc",
                "img",
                List.of()
        );

        Mockito.when(service.addRecipe(Mockito.any())).thenReturn(created);

        mockMvc.perform(post("/api/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(create)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("New"))
                .andExpect(jsonPath("$.description").value("Desc"))
                .andExpect(jsonPath("$.imageUrl").value("img"))
                .andExpect(jsonPath("$.ingredients").isArray());
    }
}