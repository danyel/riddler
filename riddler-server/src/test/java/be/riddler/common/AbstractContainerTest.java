package be.riddler.common;

import be.riddler.configuration.TestcontainersConfiguration;
import org.springframework.context.annotation.Import;

/**
 * AbstractContainerTest
 *
 * @author dnoulet
 * @version 1.0.0 06/06/2026
 */
@Import({TestcontainersConfiguration.class})
public abstract class AbstractContainerTest {
}
