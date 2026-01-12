package ch.tbz.recipe.planner.controller;

import ch.tbz.recipe.planner.domain.Recipe;
import ch.tbz.recipe.planner.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RecipeService service;
    @Test
    void testgetRecipesList() throws Exception {
        UUID uuid = UUID.randomUUID();
        Recipe recipe = new Recipe(uuid, "Test", "Desc", "img", List.of());
        Mockito.when(service.getRecipes()).thenReturn(List.of(recipe));

        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect()
    }
}
