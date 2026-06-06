package be.riddler.v1.answer.mapper;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.answer.client.model.CreateSolution;
import be.riddler.v1.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * SolutionMapperTest
 *
 * @author dnoulet
 * @version 1.0.0 31/05/2026
 */
@DisplayName("Solution Mapper")
class SolutionMapperTest extends AbstractMockTest {

    @DisplayName("Maps Create Solution to Solution Entity")
    @Test
    void fromCreateSolutionToEntity() {
        var solutionEntity = SolutionMapper.fromCreateSolutionToEntity(new CreateSolution("value"), Fixture.Answer.ANSWER_ENTITY);
        assertNotNull(solutionEntity);
        assertEquals(Fixture.Answer.ANSWER_ENTITY, solutionEntity.getAnswer());
        assertEquals("value", solutionEntity.getValue());
        assertNull(solutionEntity.getId());

    }

    @DisplayName("Maps Solution Entity to Solution")
    @Test
    void fromEntityToSolution() {
        var solution = SolutionMapper.fromEntityToSolution(Fixture.Solution.SOLUTION_ENTITY);
        assertNotNull(solution);
        assertEquals("value", solution.value());
        assertEquals(Fixture.Solution.SOLUTION_ID, solution.id());
    }
}