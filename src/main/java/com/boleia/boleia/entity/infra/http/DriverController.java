package com.boleia.boleia.entity.infra.http;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boleia.boleia.entity.application.ApproveDriver;
import com.boleia.boleia.entity.application.AtributePassword;
import com.boleia.boleia.entity.application.BanDriver;
import com.boleia.boleia.entity.application.ChangePassword;
import com.boleia.boleia.entity.application.DeclineDriver;
import com.boleia.boleia.entity.application.DriverFinder;
import com.boleia.boleia.entity.application.RecoveryPassword;
import com.boleia.boleia.entity.application.RegisterDriver;
import com.boleia.boleia.entity.application.auth.SignIn;
import com.boleia.boleia.entity.domain.DriverIsAlreadyExistsError;
import com.boleia.boleia.entity.domain.DriverNotFoundError;
import com.boleia.boleia.entity.domain.DriverOutput;
import com.boleia.boleia.entity.domain.NonMatchPasswordError;
import com.boleia.boleia.entity.domain.PasswordIsWrongError;
import com.boleia.boleia.entity.domain.PasswordLengthError;
import com.boleia.boleia.entity.domain.PasswordMustBeNumbersAndHaveSixDigitsError;
import com.boleia.boleia.entity.domain.RawAndPasswordMustProvidedError;
import com.boleia.boleia.entity.domain.SignInOutput;
import com.boleia.boleia.entity.domain.UserNotFoundError;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/boleia/api/v1")
@Tag(name = "Driver", description = "Driver management")
@RequiredArgsConstructor
public class DriverController {
    
    private final DriverInputMapper inputMapper;
    private final DriverFinder finder;
    private final RegisterDriver registerDriver;
    private final ApproveDriver approveDriver;
    private final BanDriver banDriver;
    private final DeclineDriver declineDriver;
    private final ChangePassword changePassword;
    private final AtributePassword atributePassword;
    private final RecoveryPassword recoveryPassword;
    private final SignIn signin;

    @PostMapping("/driver")
    @Operation(
        summary = "Register a new driver",
        responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(name = "DriverOutput"))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> create(@RequestBody RegisterDriverRequest body) {
        var input = this.inputMapper.toRegisterDriverInput(body);
        var out = this.registerDriver.execute(input);

        if(out.isError() && out.unwrapError().getClass().equals(DriverIsAlreadyExistsError.class)) return HttpResponse.conflict(out.unwrapError().getMsg());

        if(out.isError()) return HttpResponse.serverError(out.unwrapError().getMsg());

        return ResponseEntity.ok(out.unwrap());
    }

    @GetMapping("/driver/{id}")
    @Operation(
        summary = "Get driver by id",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "DriverOutput", implementation = DriverOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> findById(@PathVariable String id) {
        var out = finder.findById(id);
        if(out.isError() && out.unwrapError().getClass().equals(DriverNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        return ResponseEntity.ok(out.unwrap());

    }

    @PatchMapping("/driver/{id}/approve")
    @Operation(
        summary = "Approve driver",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "DriverOutput"))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> approveDriver(@PathVariable String id) {
        var out = approveDriver.execute(UUID.fromString(id));
        
        if(out.isError() && out.unwrapError().getClass().equals(DriverNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        return ResponseEntity.ok(null);
    }

    @PatchMapping("/driver/{id}/ban")
    @Operation(
        summary = "Ban driver",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "DriverOutput"))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> banDriver(@PathVariable String id) {
        var out = banDriver.execute(UUID.fromString(id));
        
        if(out.isError() && out.unwrapError().getClass().equals(DriverNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        return ResponseEntity.ok(null);
    }

    @PatchMapping("/driver/{id}/decline")
    @Operation(
        summary = "Decline driver",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "DriverOutput"))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> declineDriver(@PathVariable String id) {
        var out = declineDriver.execute(UUID.fromString(id));
        
        if(out.isError() && out.unwrapError().getClass().equals(DriverNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        return ResponseEntity.ok(null);
    }


    @GetMapping("/driver")
    @Operation(
        summary = "Get All drivers",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "DriverOutput", implementation = DriverOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<List<DriverOutput>> findAll() {
        var out = finder.findAll();
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

        if(out.isError() && out.unwrapError().getClass().equals(DriverNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        if(out.isError() && out.unwrapError().getClass().equals(NonMatchPasswordError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());

        if(out.isError() && out.unwrapError().getClass().equals(PasswordIsWrongError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());

        return ResponseEntity.ok(null);
    }

    @PostMapping("/auth/driver/signin")
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

        if(out.isError() && out.unwrapError().getClass().equals(PasswordIsWrongError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());


        return ResponseEntity.ok(out.unwrap());
    }
    
    @PatchMapping("/auth/driver/atribute-password")
    @Operation(
        summary = "Atribute a password in a driver",
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
        if(out.isError() && out.unwrapError().getClass().equals(DriverNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        if(out.isError() && out.unwrapError().getClass().equals(PasswordIsWrongError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        
        if(out.isError() && out.unwrapError().getClass().equals(RawAndPasswordMustProvidedError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(PasswordMustBeNumbersAndHaveSixDigitsError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(PasswordLengthError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());

        return ResponseEntity.ok(out.unwrap());
    }


    @PatchMapping("/auth/driver/recovery-password")
    @Operation(
        summary = "Recovery a password in a driver",
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
        if(out.isError() && out.unwrapError().getClass().equals(DriverNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        if(out.isError() && out.unwrapError().getClass().equals(PasswordIsWrongError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        
        if(out.isError() && out.unwrapError().getClass().equals(RawAndPasswordMustProvidedError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(PasswordMustBeNumbersAndHaveSixDigitsError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(PasswordLengthError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());

        return ResponseEntity.ok(out.unwrap());
    }

    @GetMapping("/driver/indentification-number/{indentificationNumber}")
    @Operation(
        summary = "Get driver by id",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "DriverOutput", implementation = DriverOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> findByIndentificationNumber(@PathVariable String indentificationNumber) {
        var out = finder.findByIdentificationNumber(indentificationNumber);
        if(out.isError() && out.unwrapError().getClass().equals(DriverNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        return ResponseEntity.ok(out.unwrap());

    }

    @GetMapping("/driver/phone-number/{phoneNumber}")
    @Operation(
        summary = "Get driver by id",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "DriverOutput", implementation = DriverOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> findByPhoneNumber(@RequestBody FindDriverByPhoneNumber body) {
        var out = finder.findByIdentificationNumber(body.phoneNumber());
        if(out.isError() && out.unwrapError().getClass().equals(DriverNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        return ResponseEntity.ok(out.unwrap());

    }

}