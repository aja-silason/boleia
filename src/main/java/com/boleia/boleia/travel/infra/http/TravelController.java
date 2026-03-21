package com.boleia.boleia.travel.infra.http;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boleia.boleia.entity.domain.DriverIsAlreadyExistsError;
import com.boleia.boleia.entity.domain.UserNotFoundError;
import com.boleia.boleia.shared.types.HttpResponse;
import com.boleia.boleia.travel.application.ApproveRequestTravel;
import com.boleia.boleia.travel.application.CreateTravel;
import com.boleia.boleia.travel.application.EvaluateUser;
import com.boleia.boleia.travel.application.FinishTravel;
import com.boleia.boleia.travel.application.RefuseRequestTravel;
import com.boleia.boleia.travel.application.RequestTravel;
import com.boleia.boleia.travel.application.StartTravel;
import com.boleia.boleia.travel.application.TravelFinder;
import com.boleia.boleia.travel.domain.TravelIsFuelError;
import com.boleia.boleia.travel.domain.TravelNotFoundError;
import com.boleia.boleia.travel.domain.TravelOutput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/boleia/api/v1")
@Tag(name = "Travels", description = "Travels management")
@RequiredArgsConstructor
public class TravelController {
    
    private final TravelInputMapper inputMapper;
    private final CreateTravel createTravel;
    private final TravelFinder finder;
    private final RequestTravel requestTravel;
    private final ApproveRequestTravel approveRequestTravel;
    private final RefuseRequestTravel refuseRequestTravel;
    private final FinishTravel finishTravel;
    private final StartTravel startTravel;
    private final EvaluateUser evaluateUser;
    
    @PostMapping("/travels")
    @Operation(
        summary = "Register a new travel",
        responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(name = "TravelOutput"))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> create(@RequestBody CreateTravelRequest body) {
        var input = this.inputMapper.toCreateTravelInput(body);
        var out = this.createTravel.execute(input);

        if(out.isError() && out.unwrapError().getClass().equals(DriverIsAlreadyExistsError.class)) return HttpResponse.conflict(out.unwrapError().getMsg());

        if(out.isError()) return HttpResponse.serverError(out.unwrapError().getMsg());

        return ResponseEntity.status(201).build();
    }


    @GetMapping("/travels/{id}")
    @Operation(
        summary = "Get travels by id",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "TravelOutput", implementation = TravelOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> findById(@PathVariable String id) {
        var out = finder.findById(id);
        if(out.isError() && out.unwrapError().getClass().equals(TravelNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        return ResponseEntity.ok(out.unwrap());

    }

    @GetMapping("/travels")
    @Operation(
        summary = "Get all travels by id",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "TravelOutput", implementation = TravelOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> findAll() {
        var out = finder.findAll();
        return ResponseEntity.ok(out.unwrap());

    }

    @GetMapping("/travels/driver/{driverId}")
    @Operation(
        summary = "Get all travels by driver",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "TravelOutput", implementation = TravelOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> findAllByDriver(@PathVariable String driverId) {
        var out = finder.findAllByDriver(driverId);
        return ResponseEntity.ok(out.unwrap());

    }

    @PostMapping("/travels/request")
    @Operation(
        summary = "Request a travel",
        responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(name = "TravelOutput"))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> requestTravel(@RequestBody TravelRequest body) {
        var input = this.inputMapper.toRequestTravelInput(body);
        var out = this.requestTravel.execute(input);

        if(out.isError() && out.unwrapError().getClass().equals(UserNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(TravelNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(TravelIsFuelError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());

        if(out.isError()) return HttpResponse.serverError(out.unwrapError().getMsg());

        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/travels/request/approve")
    @Operation(
        summary = "Request a travel",
        responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(name = "TravelOutput"))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> approveRequestTravel(@RequestBody ApproveTravelRequest body) {
        var input = this.inputMapper.toApproveRequestTravelInput(body);
        var out = this.approveRequestTravel.execute(input);

        if(out.isError() && out.unwrapError().getClass().equals(com.boleia.boleia.travel.domain.user.UserNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(TravelNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(TravelIsFuelError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());

        if(out.isError()) return HttpResponse.serverError(out.unwrapError().getMsg());

        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/travels/request/refuse")
    @Operation(
        summary = "Request a travel",
        responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(name = "TravelOutput"))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> refuseRequestTravel(@RequestBody RefuseTravelRequest body) {
        var input = this.inputMapper.toRefuseRequestTravelInput(body);
        var out = this.refuseRequestTravel.execute(input);

        if(out.isError() && out.unwrapError().getClass().equals(com.boleia.boleia.travel.domain.user.UserNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(TravelNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(TravelIsFuelError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());

        if(out.isError()) return HttpResponse.serverError(out.unwrapError().getMsg());

        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/travels/{id}/finish")
    @Operation(
        summary = "Finish travel",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "OutputResponse"))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> finishTravel(@PathVariable String id) {
        var out = finishTravel.execute(UUID.fromString(id));
        if(out.isError() && out.unwrapError().getClass().equals(TravelNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        return ResponseEntity.ok(out.unwrap());

    }

    @PatchMapping("/travels/{id}/start")
    @Operation(
        summary = "Start travel",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "OutputResponse"))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> startTravel(@PathVariable String id) {
        var out = startTravel.execute(UUID.fromString(id));
        if(out.isError() && out.unwrapError().getClass().equals(TravelNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        return ResponseEntity.ok(out.unwrap());

    }

    @PostMapping("/travels/rating/user/evaluate")
    @Operation(
        summary = "User evaluate, givin stars",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "OutputResponse"))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> evaluateUser(@RequestBody EvaluateUserRequest body) {
        var input = inputMapper.toEvaluateUserInput(body);
        var out = evaluateUser.execute(input);
        if(out.isError() && out.unwrapError().getClass().equals(com.boleia.boleia.travel.domain.user.UserNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        if(out.isError()) return HttpResponse.serverError(out.unwrapError().getMsg());

        return ResponseEntity.ok(out.unwrap());

    }

}
