package be.riddler.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * AuditTrailLogger
 *
 * @author dnoulet
 * @version 1.0.0 01/05/2026
 */
@Slf4j
@Component
public class AuditTrailLogger {
    public void log(String message) {
        log.info(message);
    }
}
