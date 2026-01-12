package ch.tbz.recipe.planner.mapper;

import ch.tbz.recipe.planner.domain.Recipe;
import ch.tbz.recipe.planner.entities.RecipeEntity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

public class RecipeEntityMapperTest {
    private final RecipeEntityMapper mapper =
            Mappers.getMapper(RecipeEntityMapper.class);

    @Test
    void shouldMapEntityDomain() {
        UUID uuid = UUID.randomUUID();
        RecipeEntity entity = new RecipeEntity();
        entity.setId(uuid);
        entity.setName("Pizza");
        entity.setDescription("Classic Italian dish");

        Recipe recipe = mapper.entityToDomain(entity);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(recipe).isNotNull();
        softly.assertThat(recipe.getId()).isEqualTo(uuid);
        softly.assertThat(recipe.getName()).isEqualTo("Pizza");
        softly.assertThat(recipe.getDescription()).isEqualTo("Classic Italian dish");
        softly.assertAll();
    }

    @Test
    void shouldMapDomainEntity() {
        UUID uuid = UUID.randomUUID();
        Recipe recipe = new Recipe();
        recipe.setId(uuid);
        recipe.setName("Pizza M");
        recipe.setDescription("Classic Italian dish M");

        RecipeEntity entity = mapper.domainToEntity(recipe);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(entity).isNotNull();
        softly.assertThat(entity.getId()).isEqualTo(uuid);
        softly.assertThat(entity.getName()).isEqualTo("Pizza M");
        softly.assertThat(entity.getDescription()).isEqualTo("Classic Italian dish M");
        softly.assertAll();
    }
}
