package be.riddler.v1.participant.feature.impl;

import be.riddler.common.AbstractMockTest;
import be.riddler.v1.category.entity.CategoryEntity;
import be.riddler.v1.category.entity.KeywordEntity;
import be.riddler.v1.category.repository.CategoryRepository;
import be.riddler.v1.participant.entity.ParticipantEntity;
import be.riddler.v1.participant.repository.ParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static be.riddler.v1.fixture.Fixture.Category.CATEGORY_ID;
import static be.riddler.v1.fixture.Fixture.Participant.PARTICIPANT_ID;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

/**
 * UploadCvFeatureImplTest
 *
 * @author dnoulet
 * @version 1.0.0 06/06/2026
 */
@DisplayName("Upload Cv Feature Test")
class UploadCvFeatureImplTest extends AbstractMockTest {
    @Mock
    private ParticipantRepository participantRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private UploadCvFeatureImpl ut;
    @Captor
    private ArgumentCaptor<List<CategoryEntity>> setCategoriesCaptor;
    private byte[] data = "".getBytes();

    @BeforeEach
    void setUp() {
        try {
            data = new ClassPathResource("Fake-Resume.pdf").getContentAsByteArray();
        } catch (IOException e) {
            fail("This can never happen");
        }
    }

    @DisplayName("This is just a test to have 100% code coverage")
    @Test
    void blankPdf() {
        var participantEntity = mock(ParticipantEntity.class);
        given(participantRepository.findById(PARTICIPANT_ID)).willReturn(of(participantEntity));
        given(categoryRepository.findAllWithKeywords()).willThrow(new IllegalArgumentException("Oops"));

        var illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> ut.uploadCv(PARTICIPANT_ID, data));
        assertEquals("Oops", illegalArgumentException.getMessage());
    }

    @DisplayName("Given a valid participant and a cv that contains keywords when searching then the found category will be given")
    @Test
    void uploadCv() {
        var participantEntity = mock(ParticipantEntity.class);
        given(participantRepository.findById(PARTICIPANT_ID)).willReturn(of(participantEntity));
        var categoryEntity = CategoryEntity.builder()
                .id(CATEGORY_ID)
                .name("Church")
                .keywords(
                        List.of(
                                KeywordEntity.builder().word("Choir").build(),
                                KeywordEntity.builder().word("Running").build()
                        )
                )
                .build();
        given(categoryRepository.findAllWithKeywords()).willReturn(List.of(categoryEntity));

        ut.uploadCv(PARTICIPANT_ID, data);

        var inOrder = inOrder(categoryRepository, participantRepository, participantEntity);
        inOrder.verify(participantRepository).findById(PARTICIPANT_ID);
        inOrder.verify(participantEntity).setCv(data);
        inOrder.verify(categoryRepository).findAllWithKeywords();
        inOrder.verify(participantEntity).setCategories(setCategoriesCaptor.capture());
        inOrder.verify(participantRepository).save(participantEntity);
        inOrder.verifyNoMoreInteractions();
        var setCategories = setCategoriesCaptor.getValue();
        assertEquals(1, setCategories.size());
        assertEquals(List.of(categoryEntity), setCategories);
    }

    @DisplayName("Given a valid participant and a cv that does not contain keywords when searching then the unclassified category will be returned")
    @Test
    void uploadCv2() {
        var participantEntity = mock(ParticipantEntity.class);
        var categoryEntity = CategoryEntity.builder()
                .id(UUID.randomUUID())
                .name("Unclassified/General")
                .build();
        given(participantRepository.findById(PARTICIPANT_ID)).willReturn(of(participantEntity));
        given(categoryRepository.findAllWithKeywords()).willReturn(List.of(CategoryEntity.builder()
                .id(CATEGORY_ID)
                .name("Church")
                .keywords(
                        List.of(
                                KeywordEntity.builder().word("Choir").build()
                        )
                )
                .build()));
        given(categoryRepository.findByNameIgnoreCase("Unclassified/General")).willReturn(of(
                categoryEntity
        ));


        ut.uploadCv(PARTICIPANT_ID, data);

        var inOrder = inOrder(categoryRepository, participantRepository, participantEntity);
        inOrder.verify(participantRepository).findById(PARTICIPANT_ID);
        inOrder.verify(participantEntity).setCv(data);
        inOrder.verify(categoryRepository).findAllWithKeywords();
        inOrder.verify(categoryRepository).findByNameIgnoreCase("Unclassified/General");
        inOrder.verify(participantEntity).setCategories(setCategoriesCaptor.capture());
        inOrder.verify(participantRepository).save(participantEntity);
        inOrder.verifyNoMoreInteractions();
        var setCategories = setCategoriesCaptor.getValue();
        assertEquals(1, setCategories.size());
        assertEquals(List.of(categoryEntity), setCategories);
    }

    @DisplayName("Given a valid participant and a cv that does not contain keywords when searching then the unclassified category will be created and returned")
    @Test
    void uploadCv3() {
        var participantEntity = mock(ParticipantEntity.class);
        var categoryEntity = CategoryEntity.builder()
                .name("Unclassified/General")
                .build();
        var categoryEntity2 = CategoryEntity.builder()
                .id(UUID.randomUUID())
                .name("Unclassified/General")
                .build();
        given(participantRepository.findById(PARTICIPANT_ID)).willReturn(of(participantEntity));
        given(categoryRepository.findAllWithKeywords()).willReturn(List.of(CategoryEntity.builder()
                .id(CATEGORY_ID)
                .name("Church")
                .keywords(
                        List.of(
                                KeywordEntity.builder().word("Choir").build()
                        )
                )
                .build()));
        given(categoryRepository.findByNameIgnoreCase("Unclassified/General")).willReturn(empty());
        given(categoryRepository.save(categoryEntity)).willReturn(categoryEntity2);

        ut.uploadCv(PARTICIPANT_ID, data);

        var inOrder = inOrder(categoryRepository, participantRepository, participantEntity);
        inOrder.verify(participantRepository).findById(PARTICIPANT_ID);
        inOrder.verify(participantEntity).setCv(data);
        inOrder.verify(categoryRepository).findAllWithKeywords();
        inOrder.verify(categoryRepository).findByNameIgnoreCase("Unclassified/General");
        inOrder.verify(categoryRepository).save(categoryEntity);
        inOrder.verify(participantEntity).setCategories(setCategoriesCaptor.capture());
        inOrder.verify(participantRepository).save(participantEntity);
        inOrder.verifyNoMoreInteractions();
        var setCategories = setCategoriesCaptor.getValue();
        assertEquals(1, setCategories.size());
        assertEquals(List.of(categoryEntity2), setCategories);
    }

    @DisplayName("Given an invalid participant when uploading cv then participant not found will be thrown")
    @Test
    void throwsParticipantNotFound() {
        given(participantRepository.findById(PARTICIPANT_ID)).willReturn(empty());

        var participantNotFound = assertThrows(ParticipantNotFound.class, () -> ut.uploadCv(PARTICIPANT_ID, data));

        assertEquals("Participant with id '" + PARTICIPANT_ID + "' not found", participantNotFound.getMessage());
    }

    @DisplayName("Invalid data that is null will throw illegal argument exception")
    @Test
    void assertNotNull() {
        var illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> ut.uploadCv(PARTICIPANT_ID, null));

        assertEquals("CV must not be null", illegalArgumentException.getMessage());
    }

    @DisplayName("Invalid data that is empty will throw illegal argument exception")
    @Test
    void isTrue() {
        var illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> ut.uploadCv(PARTICIPANT_ID, "".getBytes()));

        assertEquals("CV must not be empty", illegalArgumentException.getMessage());
    }
}