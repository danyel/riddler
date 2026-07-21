package be.riddler.v1.answer.feature.impl;

import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.answer.client.model.CreateAnswer;
import be.riddler.v1.answer.entity.SolutionEntity;
import be.riddler.v1.answer.feature.CreateAnswerFeature;
import be.riddler.v1.answer.mapper.AnswerMapper;
import be.riddler.v1.answer.mapper.SolutionMapper;
import be.riddler.v1.answer.repository.AnswerRepository;
import be.riddler.v1.answer.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static lombok.AccessLevel.PACKAGE;

/**
 * CreateAnswerFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class CreateAnswerFeatureImpl implements CreateAnswerFeature {
    private final AnswerRepository answerRepository;
    private final SolutionRepository solutionRepository;

    @Transactional
    @Override
    public @NonNull Answer create(@NonNull CreateAnswer createAnswer) {
        var answerEntity = AnswerMapper.fromCreateAnswer(createAnswer);
        answerRepository.save(answerEntity);
        var solutions = new ArrayList<SolutionEntity>();
        createAnswer.solutions()
                .forEach(createSolution -> {
                    var solutionEntity = SolutionMapper.fromCreateSolutionToEntity(createSolution, answerEntity);
                    solutionRepository.save(solutionEntity);
                    solutions.add(solutionEntity);
                });
        answerEntity.setSolutions(solutions);
        answerRepository.save(answerEntity);
        return AnswerMapper.fromAnswerEntity(answerEntity);
    }
}
