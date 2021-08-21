package com.ace.registeruser.controller;

import com.ace.registeruser.entity.UserSession;
import com.ace.registeruser.service.EmailService;
import com.ace.registeruser.vo.expire.EmailExpireRequest;
import com.ace.registeruser.vo.expire.EmailExpireResponse;
import com.ace.registeruser.vo.get.GetEmailResponse;
import com.ace.registeruser.vo.update.EmailUpdateRequest;
import com.ace.registeruser.vo.update.EmailUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
public class EmailController {

    @Autowired
    EmailService emailService;

    @Operation(summary = "Fetch user email",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER,
                            name = "userName",
                            description = "user Name",
                            required = true,
                            example = "sunil",
                            schema = @Schema(type = "string"))} )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetch email details successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetEmailResponse.class)) })
    })

    @GetMapping(value = "user/fetch/email")
    public ResponseEntity<GetEmailResponse> getEmail(@RequestAttribute("userSession") Object userSession) {

        UserSession userSessionInfo=(UserSession) userSession;
        GetEmailResponse getEmailResponse = emailService.getEmail(userSessionInfo.getUserName(),userSessionInfo);

        return new ResponseEntity<GetEmailResponse>(getEmailResponse, HttpStatus.OK);
    }

    @Operation(description = "Update user email details",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER,
                            name = "userName",
                            description = "user Name",
                            required = true,
                            example = "sunil",
                            schema = @Schema(type = "string"))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " Updated user email successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmailUpdateResponse.class)) })
    })
    @PostMapping(value="user/update/email")
    public ResponseEntity<EmailUpdateResponse> updateEmailAddress(@RequestBody @Valid EmailUpdateRequest emailUpdateRequest, @RequestAttribute("userSession") Object userSession){

        UserSession userSessionInfo=(UserSession) userSession;
        emailService.updateEmail(emailUpdateRequest,userSessionInfo);

        return new ResponseEntity<EmailUpdateResponse>(
                EmailUpdateResponse
                        .builder()
                        .emailAddressIsUpdated(true)
                        .build(),
                HttpStatus.OK);
    }


    @Operation(summary = "Expire user email",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER,
                            name = "userName",
                            description = "user Name",
                            required = true,
                            example = "sunil",
                            schema = @Schema(type = "string"))} )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " user phone expired successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmailExpireResponse.class)) })
    })
    @DeleteMapping(value="user/expire/email")
    public ResponseEntity<EmailExpireResponse> expireEmail(@RequestBody @Valid EmailExpireRequest expireEmailRequest,
                                                           @RequestAttribute("userSession") Object userSession ){

        UserSession userSessionInfo=(UserSession)userSession;

        emailService.expireEmail(expireEmailRequest,userSessionInfo);

        return new ResponseEntity<EmailExpireResponse>(EmailExpireResponse
                .builder()
                .emailIsExpired(true)
                .build(),
                HttpStatus.OK);
    }
}
