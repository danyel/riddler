package be.riddler.v1.participant.feature.impl;

import be.riddler.v1.category.entity.CategoryEntity;
import be.riddler.v1.category.repository.CategoryRepository;
import be.riddler.v1.participant.feature.UploadCvFeature;
import be.riddler.v1.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PACKAGE;
import static org.apache.pdfbox.Loader.loadPDF;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

/**
 * UploadCvFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@Slf4j
@Service
@RequiredArgsConstructor(access = PACKAGE)
class UploadCvFeatureImpl implements UploadCvFeature {
    private final ParticipantRepository participantRepository;
    private final CategoryRepository categoryRepository;
    public static final CategoryEntity unclassifiedCategory = CategoryEntity.builder().name("Unclassified/General").build();

    @Transactional
    public void uploadCv(UUID participantId, byte[] cvBytes) {
        notNull(cvBytes, "CV must not be null");
        isTrue(cvBytes.length > 0, "CV must not be empty");
        var participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new ParticipantNotFound(participantId));
        participant.setCv(cvBytes);
        var categories = categoryRepository.findAllWithKeywords();

        try {
            String cvText = extractText(cvBytes);
            List<CategoryEntity> foundCategories = extractCategories(cvText, categories);
            participant.setCategories(foundCategories);
            log.info("CV processed successfully for participant {}. Assigned categories: {}", participantId, foundCategories.stream().map(CategoryEntity::getName).toList());
        } catch (Exception e) {
            log.error("Failed to parse and extract text indexes from participant CV: {}", participantId, e);
        }
        participantRepository.save(participant);
    }

    private static String extractText(byte[] pdfBytes) throws IOException {
        try (PDDocument pdDocument = loadPDF(new RandomAccessReadBuffer(pdfBytes))) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            return pdfTextStripper.getText(pdDocument);
        }
    }

    private List<CategoryEntity> extractCategories(String text, List<CategoryEntity> categories) {
        String lowerCaseText = text.toLowerCase();
        List<CategoryEntity> matchedCategories = categories.stream()
                .filter(categoryEntity -> categoryEntity.getKeywords()
                        .stream()
                        .map(keywordEntity -> keywordEntity.getWord().toLowerCase())
                        .filter(lowerCaseText::contains)
                        .count() > 1)
                .collect(Collectors.toList());


        return matchedCategories.isEmpty() ?
                new ArrayList<>(List.of(categoryRepository.findByNameIgnoreCase("Unclassified/General")
                        .orElseGet(() -> categoryRepository.save(unclassifiedCategory)))) :
                matchedCategories;
    }
}
