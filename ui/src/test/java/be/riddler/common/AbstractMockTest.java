package be.riddler.common;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

/**
 * AbstractMockTest
 *
 * @author dnoulet
 * @version 1.0.0 06/06/2026
 */
@ActiveProfiles("test")
@TestMethodOrder(value = MethodOrderer.DisplayName.class)
@ExtendWith(MockitoExtension.class)
public abstract class AbstractMockTest {
}
