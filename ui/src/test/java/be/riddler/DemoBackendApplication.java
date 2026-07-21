package be.riddler;

import org.springframework.boot.SpringApplication;
import org.springframework.test.context.ActiveProfiles;

/**
 * DemoApplication
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
@ActiveProfiles("test")
public class DemoBackendApplication {
    static void main(String[] args) {
        SpringApplication.from(UIApplication::main)
                .run(args);
    }
}
