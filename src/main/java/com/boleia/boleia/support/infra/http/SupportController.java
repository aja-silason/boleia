package com.boleia.boleia.support.infra.http;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boleia.boleia.entity.application.DeactiveUser;
import com.boleia.boleia.shared.types.HttpResponse;
import com.boleia.boleia.support.application.AtributeSystemInformation;
import com.boleia.boleia.support.application.ChantSupportFinder;
import com.boleia.boleia.support.application.PoliticsAndTermsFinder;
import com.boleia.boleia.support.application.RequestSupport;
import com.boleia.boleia.support.application.SystemInformationFinder;
import com.boleia.boleia.support.domain.chatSupport.ChatSupportNotFoundError;
import com.boleia.boleia.support.domain.chatSupport.ChatSupportOutput;
import com.boleia.boleia.support.domain.chatSupport.SupportMustHaveMessageError;
import com.boleia.boleia.support.domain.politicsAndTerms.TermsAndPolitcsOutput;
import com.boleia.boleia.support.domain.system.SystemDataInformationCannotBeEmptyError;
import com.boleia.boleia.support.domain.system.SystemInformationOutput;
import com.boleia.boleia.support.domain.user.UserNotFoundError;

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
@Tag(name = "Support", description = "Support management")
@RequiredArgsConstructor
public class SupportController {

    private final SupportInputMapper inputMapper;
    private final RequestSupport requestSupport;
    private final ChantSupportFinder finder;
    private final SystemInformationFinder systemfinder;
    private final AtributeSystemInformation atributeSystemInformation;
    private final DeactiveUser deactiveUser;
    private final PoliticsAndTermsFinder politicsAndTermsFinder;
    
    @PostMapping("/settings/support")
    @Operation(
        summary = "Request a support",
        responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(name = "ChatSupportOutput", exampleClasses = ChatSupportOutput.class, implementation = ChatSupportOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> requestSupport(@RequestBody SupportRequest body) {
        var input = this.inputMapper.toRequestSupportInput(body);
        var out = this.requestSupport.execute(input);

        if(out.isError() && out.unwrapError().getClass().equals(SupportMustHaveMessageError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());

        if(out.isError() && out.unwrapError().getClass().equals(UserNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        if(out.isError()) return HttpResponse.serverError(out.unwrapError().getMsg());

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/settings/support")
    @Operation(
        summary = "Get a chat support information",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "TravelOutput", implementation = ChatSupportOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> findAll() {
        var out = finder.findAll();
        return ResponseEntity.ok(out.unwrap());

    }


    @GetMapping("/settings/support/{id}")
    @Operation(
        summary = "Get a chat support information",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "TravelOutput", implementation = ChatSupportOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> findById(@PathVariable String id) {
        var out = finder.findById(id);
        if(out.isError() && out.unwrapError().getClass().equals(ChatSupportNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        return ResponseEntity.ok(out.unwrap());
    }

    @GetMapping("/settings/system-information")
    @Operation(
        summary = "Get a system information",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "TravelOutput", implementation = SystemInformationOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> getSystemInformation() {
        var out = systemfinder.getInformation();
        return ResponseEntity.ok(out.unwrap());
    }

    @PostMapping("/settings/system-information")
    @Operation(
        summary = "Request a support",
        responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(name = "ChatSupportOutput", exampleClasses = ChatSupportOutput.class, implementation = ChatSupportOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> addSystemInformation(@RequestBody SystemInformationRequest body) {
        var input = this.inputMapper.toAtributeSystemInformationInput(body);
        var out = this.atributeSystemInformation.execute(input);

        if(out.isError() && out.unwrapError().getClass().equals(SystemDataInformationCannotBeEmptyError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());

        if(out.isError()) return HttpResponse.serverError(out.unwrapError().getMsg());

        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/settings/user/deactive")
    @Operation(
        summary = "Request a support",
        responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(name = "ChatSupportOutput", exampleClasses = ChatSupportOutput.class, implementation = ChatSupportOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> deativeUser(@RequestParam String id) {
        var out = this.deactiveUser.execute(UUID.fromString(id));

        if(out.isError() && out.unwrapError().getClass().equals(com.boleia.boleia.entity.domain.UserNotFoundError.class)) return HttpResponse.badRequest(out.unwrapError().getMsg());

        if(out.isError()) return HttpResponse.serverError(out.unwrapError().getMsg());

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/settings/politcs")
    @Operation(
        summary = "Get a politcs",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "TermsAndPolitcsOutput", implementation = TermsAndPolitcsOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> getAllPolitcs() {
        var out = politicsAndTermsFinder.findAllPolitcs();
        return ResponseEntity.ok(out.unwrap());
    }

    @GetMapping("/settings/terms")
    @Operation(
        summary = "Get a terms",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "TermsAndPolitcsOutput", implementation = TermsAndPolitcsOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> getAllTerms() {
        var out = politicsAndTermsFinder.findAllTerms();
        return ResponseEntity.ok(out.unwrap());
    }

}
