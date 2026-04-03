package com.boleia.boleia.shared.infra.http;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boleia.boleia.shared.application.SendOTP;
import com.boleia.boleia.shared.application.ValidationOtp;
import com.boleia.boleia.shared.domain.notification.OtpExpiredError;
import com.boleia.boleia.shared.domain.notification.OtpNotFoundError;
import com.boleia.boleia.shared.domain.notification.OtpNotMatchError;
import com.boleia.boleia.shared.types.HttpResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/boleia/api/v1")
@Tag(name = "One Time Password", description = "OTP Service")
@RequiredArgsConstructor
public class NotificationController {
    private final SendOTP sendOTP;
    private final ValidationOtp validationOtp;
    private final NotificationInputMapper inputMapper;

    
    @PostMapping("/otp")
    @Operation(
        summary = "Request a OTP",
        responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(name = "Output"))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> create(@RequestBody SendOTPRequest body) {
        this.sendOTP.execute(body.phoneNumber());
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/otp/valid")
    @Operation(
        summary = "Valid a OTP",
        responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(name = "Output"))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> create(@RequestBody ValidationOtpRequest body) {
        var input = inputMapper.toValidationOtpInput(body);
        var out = this.validationOtp.execute(input);

        if(out.isError() && out.unwrapError().getClass().equals(OtpNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(OtpExpiredError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(OtpNotMatchError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());

        return ResponseEntity.status(200).body(out.unwrap());
    }
    
}
