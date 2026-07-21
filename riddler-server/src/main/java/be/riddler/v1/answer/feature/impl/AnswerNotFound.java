package be.riddler.v1.answer.feature.impl;

import be.riddler.v1.common.http.NotFound;

import java.util.UUID;

/**
 * AnswerNotFound
 *
 * @author dnoulet
 * @version 1.0.0 03/06/2026
 */
public class AnswerNotFound extends NotFound {
    public AnswerNotFound(UUID id) {
        super("Answer", id);
    }
}
