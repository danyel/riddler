package be.riddler.v1.log.bff;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import org.jspecify.annotations.NonNull;

/**
 * Logger
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
@AnonymousAllowed
@BrowserCallable
public class Logger {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger("Vaadin UI");

    public boolean isDebug() {
        return LOGGER.isDebugEnabled();
    }

    public void debug(@NonNull String message) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(message);
        }
    }

    public void info(@NonNull String message) {
        LOGGER.info(message);
    }

    public void error(@NonNull String message) {
        LOGGER.error(message);
    }

    public void warn(@NonNull String message) {
        LOGGER.warn(message);
    }
}
