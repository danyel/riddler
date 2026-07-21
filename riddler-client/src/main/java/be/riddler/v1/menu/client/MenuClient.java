package be.riddler.v1.menu.client;

import be.riddler.common.tag.Tags;
import be.riddler.v1.menu.client.model.Menu;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

/**
 * MenuApi
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@Tag(name = Tags.MENU)
public interface MenuClient {

    @Operation(
            method = "GET",
            tags = Tags.MENU,
            summary = "Retrieves the menu of the logged in user",
            operationId = "menu",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Menu.class)))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetExchange
    @ResponseStatus(HttpStatus.OK)
    List<Menu> menu();
}
