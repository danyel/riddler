package be.riddler.configuration;

import be.riddler.v1.activity.client.ActivityClient;
import be.riddler.v1.answer.client.AnswerClient;
import be.riddler.v1.category.client.CategoryClient;
import be.riddler.v1.invitation.client.InvitationClient;
import be.riddler.v1.menu.client.MenuClient;
import be.riddler.v1.participant.client.ParticipantClient;
import be.riddler.v1.publication.client.PublicationClient;
import be.riddler.v1.question.client.QuestionClient;
import be.riddler.v1.security.client.SecurityClient;
import be.riddler.v1.settings.client.SettingsClient;
import be.riddler.v1.ui.client.UiClient;
import jakarta.servlet.http.Cookie;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static be.riddler.common.path.Paths.ACTIVITY;
import static be.riddler.common.path.Paths.ANSWER;
import static be.riddler.common.path.Paths.CATEGORY;
import static be.riddler.common.path.Paths.INVITATION;
import static be.riddler.common.path.Paths.MENU;
import static be.riddler.common.path.Paths.PARTICIPANT;
import static be.riddler.common.path.Paths.PUBLICATION;
import static be.riddler.common.path.Paths.QUESTION;
import static be.riddler.common.path.Paths.SECURITY;
import static be.riddler.common.path.Paths.SETTING;
import static be.riddler.common.path.Paths.UI;

/**
 * ClientConfiguration
 * Back end configuration
 *
 * @author dnoulet
 * @version 1.0.0 12/07/2026
 */
@Configuration
class ClientConfiguration {
    @Value("${riddler.server}")
    private String riddlerServer;

    @Bean
    ActivityClient activityClient() {
        return createClient(ActivityClient.class, ACTIVITY);
    }

    @Bean
    AnswerClient answerClient() {
        return createClient(AnswerClient.class, ANSWER);
    }

    @Bean
    SettingsClient settingsClient() {
        return createClient(SettingsClient.class, SETTING);
    }

    @Bean
    CategoryClient categoryClient() {
        return createClient(CategoryClient.class, CATEGORY);
    }

    @Bean
    InvitationClient invitationClient() {
        return createClient(InvitationClient.class, INVITATION);
    }

    @Bean
    QuestionClient questionClient() {
        return createClient(QuestionClient.class, QUESTION);
    }

    @Bean
    MenuClient menuClient() {
        return createClient(MenuClient.class, MENU);
    }

    @Bean
    ParticipantClient participantClient() {
        return createClient(ParticipantClient.class, PARTICIPANT);
    }

    @Bean
    PublicationClient publicationClient() {
        return createClient(PublicationClient.class, PUBLICATION);
    }

    @Bean
    UiClient uiClient() {
        return createClient(UiClient.class, UI);
    }

    @Bean
    SecurityClient securityClient() {
        return createClient(SecurityClient.class, SECURITY);
    }

    private <C> C createClient(Class<C> clazz, String contextPath) {
        var log = LoggerFactory.getLogger(clazz);
        var bufferingFactory = new BufferingClientHttpRequestFactory(
                new SimpleClientHttpRequestFactory()
        );
        var restClient = RestClient.builder()
                .baseUrl("%s/%s".formatted(riddlerServer, contextPath))
                .requestFactory(bufferingFactory)
                .requestInterceptor((request, body, execution) -> {
                    var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    if (attributes != null) {
                        var servletRequest = attributes.getRequest();
                        var cookies = servletRequest.getCookies();

                        // settings security for the rest calls to the backend
                        if (cookies != null) {
                            var headerAndPayload = Arrays.stream(cookies)
                                    .filter(c -> "jwt.headerAndPayload".equals(c.getName()))
                                    .map(Cookie::getValue)
                                    .findFirst()
                                    .orElse(null);

                            var signature = Arrays.stream(cookies)
                                    .filter(c -> "jwt.signature".equals(c.getName()))
                                    .map(Cookie::getValue)
                                    .findFirst()
                                    .orElse(null);
                            if (headerAndPayload != null && signature != null) {
                                var combinedToken = headerAndPayload + "." + signature;
                                request.getHeaders().set(HttpHeaders.AUTHORIZATION, combinedToken);
                            }
                        }
                    }
                    return execution.execute(request, body);
                })
                .requestInterceptor((request, body, execution) -> {
                    // Log Outgoing Request Details
                    var requestBody = body.length > 0 ? new String(body, StandardCharsets.UTF_8) : "[Empty Body]";
                    log.info("""
                                    ========== [OUTGOING CLIENT REQUEST] ==========
                                    URI     : {} {}
                                    Headers : {}
                                    Body    : {}
                                    ===============================================
                                    """,
                            request.getMethod(), request.getURI(), request.getHeaders(), requestBody);

                    // Execute request
                    var response = execution.execute(request, body);

                    // Safely read response payload twice (once for logs, once for your application list deserializer)
                    var responseBody = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
                    if (responseBody.isBlank()) {
                        responseBody = "[Empty Response Body]";
                    }

                    log.info("""
                                    ========== [INCOMING CLIENT RESPONSE] ==========
                                    Status  : {} {}
                                    Headers : {}
                                    Body    : {}
                                    ================================================
                                    """,
                            response.getStatusCode(), response.getStatusText(), response.getHeaders(), responseBody);

                    return response;
                })
                .build();
        var adapter = RestClientAdapter.create(restClient);
        var factory = HttpServiceProxyFactory.builderFor(adapter)
                .build();
        return factory.createClient(clazz);
    }
}