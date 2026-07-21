package be.riddler.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.List;

/**
 * OpenApiConfiguration
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@Configuration
@OpenAPIDefinition
@SecuritySchemes(
        value = {
                @SecurityScheme(
                        name = HttpHeaders.AUTHORIZATION,
                        type = SecuritySchemeType.APIKEY,
                        in = SecuritySchemeIn.HEADER,
                        paramName = HttpHeaders.AUTHORIZATION
                )
        }
)
class OpenApiConfiguration {
    @Bean
    OpenAPI openAPI(
            @Value("${swagger.root:http://localhost:8080}") String swaggerUrl,
            @Value("${spring.application.name:riddler-api}") String applicationName,
            @Value("${spring.application.version:1.0.0}") String version
    ) {
        var info = new Info()
                .title(applicationName)
                .description("Open API definition for the Riddler API")
                .version(version)
                .contact(new Contact().name("Daniel Noulet"));

        var server = new Server().url(swaggerUrl);

        // Name must match the @SecurityScheme's name exactly
        var securityRequirement = new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION);

        return new OpenAPI()
                .servers(List.of(server))
                .security(List.of(securityRequirement))
                .info(info);
    }
}
