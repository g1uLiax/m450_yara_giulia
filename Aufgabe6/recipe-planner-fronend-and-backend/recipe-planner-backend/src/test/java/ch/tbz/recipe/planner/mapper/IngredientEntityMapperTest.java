package ch.tbz.recipe.planner.mapper;

import ch.tbz.recipe.planner.domain.Ingredient;
import ch.tbz.recipe.planner.entities.IngredientEntity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

public class IngredientEntityMapperTest {
    private final IngredientEntityMapper mapper = Mappers.getMapper(IngredientEntityMapper.class);

    @Test
    void shouldMapEntityDomain() {
        UUID uuid = UUID.randomUUID();
        IngredientEntity entity = new IngredientEntity();
        entity.setId(uuid);
        entity.setName("Pepper");
        entity.setAmount(20);

        Ingredient ingredient = mapper.entityToDomain(entity);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ingredient).isNotNull();
        softly.assertThat(ingredient.getId()).isEqualTo(uuid);
        softly.assertThat(ingredient.getName()).isEqualTo("Pepper");
        softly.assertThat(ingredient.getAmount()).isEqualTo(20);
        softly.assertAll();
    }

    @Test
    void shouldMapDomainListToEntityList() {
        UUID uuid = UUID.randomUUID();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(uuid);
        ingredient.setName("Flour");
        ingredient.setAmount(500);

        List<IngredientEntity> entities =
                mapper.domainsToEntities(List.of(ingredient));

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(entities).hasSize(1);

        IngredientEntity entity = entities.get(0);
        softly.assertThat(entity.getId()).isEqualTo(uuid);
        softly.assertThat(entity.getName()).isEqualTo("Flour");
        softly.assertThat(entity.getAmount()).isEqualTo(500);
        softly.assertAll();
    }

    @Test
    void shouldMapEntityListToDomainList() {
        UUID uuid = UUID.randomUUID();
        IngredientEntity entity = new IngredientEntity();
        entity.setId(uuid);
        entity.setName("Sugar");
        entity.setAmount(100);

        List<Ingredient> ingredients =
                mapper.entitiesToDomains(List.of(entity));
        Ingredient ingredient = ingredients.get(0);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ingredient).hasSize(1);
        softly.assertThat(ingredients.getId())
    }
}
