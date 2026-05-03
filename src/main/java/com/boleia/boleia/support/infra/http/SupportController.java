package com.boleia.boleia.support.infra.http;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boleia.boleia.shared.types.HttpResponse;
import com.boleia.boleia.support.application.RequestSupport;
import com.boleia.boleia.support.domain.chatSupport.ChatSupportOutput;
import com.boleia.boleia.support.domain.chatSupport.SupportMustHaveMessageError;
import com.boleia.boleia.support.domain.user.UserNotFoundError;

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
@Tag(name = "Support", description = "Support management")
@RequiredArgsConstructor
public class SupportController {

    private final SupportInputMapper inputMapper;
    private final RequestSupport requestSupport;
    

    
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


    // @GetMapping("/travels/{id}")
    // @Operation(
    //     summary = "Get travels by id",
    //     responses = {
    //         @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "TravelOutput", implementation = TravelOutput.class))),
    //         @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
    //         @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
    //     }
    // )
    // public ResponseEntity<?> findById(@PathVariable String id) {
    //     var out = finder.findById(id);
    //     if(out.isError() && out.unwrapError().getClass().equals(TravelNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

    //     return ResponseEntity.ok(out.unwrap());

    // }

}
