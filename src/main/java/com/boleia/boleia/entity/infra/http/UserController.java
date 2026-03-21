package com.boleia.boleia.entity.infra.http;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boleia.boleia.entity.application.RegisterUser;
import com.boleia.boleia.entity.application.UserFinder;
import com.boleia.boleia.entity.domain.PhoneNumberIsAlreadyExistsError;
import com.boleia.boleia.entity.domain.UserNotFoundError;
import com.boleia.boleia.entity.domain.UserOutput;
import com.boleia.boleia.shared.types.HttpResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/boleia/api/v1")
@Tag(name = "User", description = "User management")
@RequiredArgsConstructor
public class UserController {

    private final UserFinder finder;
    private final RegisterUser registerUser;
    private final UserInputMapper inputMapper;
    
    @PostMapping("/user")
    @Operation(
        summary = "Register a user",
        responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(name = "GenerateStampsResponse"))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> create(@RequestBody RegisterUserRequest body) {
        var input = inputMapper.toRegisterUserInput(body);
        var out = registerUser.execute(input);

        if(out.isError() && out.unwrapError().getClass().equals(UserNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(PhoneNumberIsAlreadyExistsError.class)) return HttpResponse.conflict(out.unwrapError().getMsg());
        if(out.isError()) return HttpResponse.serverError(out.unwrapError().getMsg());

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/passanger/{id}")
    @Operation(
        summary = "Get user by id",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "GenerateStampsResponse", implementation = UserOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> findById(@RequestParam UUID id) {
        var out = finder.findById(id.toString());
        if(out.isError() && out.unwrapError().getClass().equals(UserNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        return ResponseEntity.ok(out.unwrap());
    }


@GetMapping("/user/{phoneNumber}")
    @Operation(
        summary = "Get user by phone number",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "GenerateStampsResponse", implementation = UserOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> findByPhoneNumber(@RequestParam String phoneNumber) {
        var out = finder.findByPhoneNumber(phoneNumber);
        if(out.isError() && out.unwrapError().getClass().equals(UserNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        return ResponseEntity.ok(out.unwrap());
    }


    @GetMapping("/user")
    @Operation(
        summary = "Get All user",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "GenerateStampsResponse", implementation = UserOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<List<UserOutput>> findAll() {
        var out = finder.findAll();
        return ResponseEntity.ok(out.unwrap());
    }

    
}
