package be.riddler.v1.question.feature;

import be.riddler.v1.question.client.model.AddOption;
import be.riddler.v1.question.client.model.Option;
import org.jspecify.annotations.NonNull;

/**
 * AddOptionFeature
 *
 * @author dnoulet
 * @version 1.0.0 26/05/2026
 */
public interface AddOptionFeature {
    Option addOption(@NonNull AddOption addOption);
}
