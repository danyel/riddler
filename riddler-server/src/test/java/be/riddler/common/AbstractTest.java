package be.riddler.common;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.test.context.ActiveProfiles;

/**
 * AbstractTest
 *
 * @author dnoulet
 * @version 1.0.0 06/06/2026
 */

@ActiveProfiles("test")
@TestMethodOrder(value = MethodOrderer.DisplayName.class)
public abstract class AbstractTest {
}
