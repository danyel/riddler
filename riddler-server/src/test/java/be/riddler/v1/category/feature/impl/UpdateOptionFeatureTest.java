package be.riddler.v1.category.feature.impl;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.question.client.model.UpdateOption;
import be.riddler.v1.question.entity.OptionEntity;
import be.riddler.v1.question.repository.OptionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static be.riddler.v1.fixture.Fixture.Option.OPTION_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * UpdateOptionFeatureTest
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
@DisplayName("Update Option Feature")
class UpdateOptionFeatureTest extends AbstractMockTest {
    @Mock
    private OptionRepository optionRepository;
    @InjectMocks
    private UpdateOptionFeatureImpl ut;

    /**
     * Given:
     * - a valid option id (11111111-1111-1111-1111-111111111111) and a value to replace the original value
     * When:
     * Updating the option
     * Then:
     * - fetch the option
     * - value will be set
     * - save the option
     */
    @DisplayName("Given an option id when updating the option then the value will be set")
    @Test
    void updateOption() {
        var optionEntity = mock(OptionEntity.class);
        when(optionEntity.getId()).thenReturn(OPTION_ID);
        when(optionEntity.getValue()).thenReturn("value");
        when(optionRepository.findById(eq(OPTION_ID))).thenReturn(Optional.of(optionEntity));

        var updateOption = new UpdateOption(OPTION_ID, "value");
        var option = ut.updateOption(updateOption);

        var inOrder = inOrder(optionRepository, optionEntity);
        inOrder.verify(optionRepository).findById(eq(OPTION_ID));
        inOrder.verify(optionEntity).setValue("value");
        inOrder.verify(optionRepository).save(optionEntity);
        //noinspection ResultOfMethodCallIgnored
        inOrder.verify(optionEntity).getId();
        //noinspection ResultOfMethodCallIgnored
        inOrder.verify(optionEntity).getValue();
        inOrder.verifyNoMoreInteractions();

        assertEquals("value", option.value());
        assertEquals(OPTION_ID, option.id());
    }


    /**
     * Given:
     * - a valid option id (11111111-1111-1111-1111-111111111111) and a value to replace the original value
     * When:
     * Updating the option
     * Then:
     * - fetch the option
     * - throw illegal state exception because the option does not exist
     */
    @DisplayName("Given an invalid option id when updating the option then illegal state exception will be thrown")
    @Test
    void updateOptionThrowsException() {
        when(optionRepository.findById(OPTION_ID)).thenReturn(Optional.empty());
        var updateOption = new UpdateOption(OPTION_ID, "value");

        var illegalStateException = assertThrows(IllegalStateException.class, () -> ut.updateOption(updateOption));

        assertEquals("Option with id %s not found".formatted(OPTION_ID), illegalStateException.getMessage());
    }
}