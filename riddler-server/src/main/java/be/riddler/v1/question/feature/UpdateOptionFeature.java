package be.riddler.v1.question.feature;

import be.riddler.v1.question.client.model.Option;
import be.riddler.v1.question.client.model.UpdateOption;
import org.jspecify.annotations.NonNull;

/**
 * UpdateOptionFeature
 *
 * @author dnoulet
 * @version 1.0.0 29/05/2026
 */
public interface UpdateOptionFeature {
    @NonNull Option updateOption(@NonNull UpdateOption updateOption);
}
