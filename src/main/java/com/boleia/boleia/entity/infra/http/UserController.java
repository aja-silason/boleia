package com.boleia.boleia.entity.infra.http;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boleia.boleia.entity.application.AtributePassword;
import com.boleia.boleia.entity.application.ChangePassword;
import com.boleia.boleia.entity.application.RecoveryPassword;
import com.boleia.boleia.entity.application.RegisterUser;
import com.boleia.boleia.entity.application.UserFinder;
import com.boleia.boleia.entity.application.auth.SignIn;
import com.boleia.boleia.entity.domain.NonMatchPasswordError;
import com.boleia.boleia.entity.domain.PasswordIsWrongError;
import com.boleia.boleia.entity.domain.PasswordLengthError;
import com.boleia.boleia.entity.domain.PasswordMustBeNumbersAndHaveSixDigitsError;
import com.boleia.boleia.entity.domain.PhoneNumberIsAlreadyExistsError;
import com.boleia.boleia.entity.domain.RawAndPasswordMustProvidedError;
import com.boleia.boleia.entity.domain.SignInOutput;
import com.boleia.boleia.entity.domain.UserIsAlreadyBanedError;
import com.boleia.boleia.entity.domain.UserIsAlreadyDeactivatedError;
import com.boleia.boleia.entity.domain.UserIsAlreadyDeclinedError;
import com.boleia.boleia.entity.domain.UserIsAlreadyDelitedError;
import com.boleia.boleia.entity.domain.UserIsAlreadyPendingError;
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
import org.springframework.web.bind.annotation.PatchMapping;
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
    private final ChangePassword changePassword;
    private final AtributePassword atributePassword;
    private final RecoveryPassword recoveryPassword;
    private final SignIn signin;
    
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



    @PostMapping("/auth/user/signin")
    @Operation(
        summary = "Sign in into system",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "SigInOutput",implementation = SignInOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> signIn(@RequestBody SignInRequest body) {

        var input = inputMapper.toSignInInput(body);
        var out = signin.execute(input);

        if(out.isError() && out.unwrapError().getClass().equals(UserNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());
        
        if(out.isError() && out.unwrapError().getClass().equals(UserIsAlreadyDeactivatedError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(UserIsAlreadyBanedError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(UserIsAlreadyDeclinedError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(UserIsAlreadyPendingError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(UserIsAlreadyDelitedError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());

        if(out.isError() && out.unwrapError().getClass().equals(PasswordIsWrongError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(RawAndPasswordMustProvidedError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());


        return ResponseEntity.ok(out.unwrap());
    }
    
    @PatchMapping("/auth/user/atribute-password")
    @Operation(
        summary = "Atribute a password in a user",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "",implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> atributePassword(@RequestBody AtributePasswordRequest body) {

        var input = inputMapper.toAtributePasswordInput(body);
        var out = atributePassword.execute(input);

        if(out.isError() && out.unwrapError().getClass().equals(UserNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        if(out.isError() && out.unwrapError().getClass().equals(PasswordIsWrongError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        
        if(out.isError() && out.unwrapError().getClass().equals(RawAndPasswordMustProvidedError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(PasswordMustBeNumbersAndHaveSixDigitsError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(PasswordLengthError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());

        return ResponseEntity.ok(out.unwrap());
    }


    @PatchMapping("/auth/user/recovery-password")
    @Operation(
        summary = "Recovery a password in a user",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "",implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> recoveryPassword(@RequestBody RecoveryPasswordRequest body) {

        var input = inputMapper.toRecoveryPassworInput(body);
        var out = recoveryPassword.execute(input);

        if(out.isError() && out.unwrapError().getClass().equals(UserNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        if(out.isError() && out.unwrapError().getClass().equals(PasswordIsWrongError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        
        if(out.isError() && out.unwrapError().getClass().equals(RawAndPasswordMustProvidedError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(PasswordMustBeNumbersAndHaveSixDigitsError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(PasswordLengthError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());

        return ResponseEntity.ok(out.unwrap());
    }

    @PatchMapping("/driver/{id}/change-password")
    @Operation(
        summary = "Change driver password",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "DriverOutput"))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> changeDriverPassword(@RequestBody ChangePasswordRequest body) {

        var input = inputMapper.toChangePasswordInput(body);
        var out = changePassword.execute(input);

        if(out.isError() && out.unwrapError().getClass().equals(UserNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        if(out.isError() && out.unwrapError().getClass().equals(NonMatchPasswordError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());

        if(out.isError() && out.unwrapError().getClass().equals(PasswordIsWrongError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());

        return ResponseEntity.ok(null);
    }

    // @PatchMapping("/user/fcm")
    // @Operation(
    //     summary = "Add FCM into user to get Push Notifications",
    //     responses = {
    //         @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "GenerateStampsResponse", implementation = UserOutput.class))),
    //         @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
    //         @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
    //     }
    // )
    // public ResponseEntity<?> findById(@RequestBody FirebaseTokenMessageRequest body) {
    //     var input = this.inputMapper.toFirebaseTokenMessageInput(body);
    //     var out = this.addFirebaseTokenMessage.execute(input);

    //     if(out.isError() && out.unwrapError().getClass().equals(UserNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());
    //     if(out.isError() && out.unwrapError().getClass().equals(DomainError.class)) return HttpResponse.serverError(out.unwrapError().getMsg());

    //     return ResponseEntity.ok(out.unwrap());
    // }

    
}
