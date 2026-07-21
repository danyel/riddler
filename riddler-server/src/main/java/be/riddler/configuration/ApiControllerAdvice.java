package be.riddler.configuration;

import be.riddler.v1.common.http.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;

/**
 * ApiControllerAdvice
 *
 * @author dnoulet
 * @version 1.0.0 03/05/2026
 */
@ControllerAdvice
class ApiControllerAdvice {

    @ExceptionHandler(NotFound.class)
    ResponseEntity<ProblemDetail> handleEntityNotFoundException(NotFound ex, WebRequest webRequest) {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle(ex.getTitle());
        problemDetail.setDetail(ex.getDescription());
        problemDetail.setInstance(URI.create(webRequest.getContextPath()));
        problemDetail.setProperties(ex.getProperties());

        return new ResponseEntity<>(problemDetail, HttpStatus.NOT_FOUND);
    }
}
