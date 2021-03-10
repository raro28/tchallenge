package mx.ekthor.challenge.rest.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import mx.ekthor.challenge.rest.models.requests.PostPlayer;
import mx.ekthor.challenge.rest.models.responses.Result;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping("/players")
public interface PlayerAPI {
    @Operation(summary = "Process a list of players", tags = {"crud"})
    @ApiResponse(responseCode = "200", description = "Players processed")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Result> process(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The players to process") @RequestBody final PostPlayer rq);
}