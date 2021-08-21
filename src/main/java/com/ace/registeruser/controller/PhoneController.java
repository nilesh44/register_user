package com.ace.registeruser.controller;

import com.ace.registeruser.entity.UserSession;
import com.ace.registeruser.service.PhoneService;
import com.ace.registeruser.vo.expire.PhoneExpireRequest;
import com.ace.registeruser.vo.expire.PhoneExpireResponse;
import com.ace.registeruser.vo.get.GetUserPhoneResponse;
import com.ace.registeruser.vo.phone.AddSecondaryPhoneNumber;
import com.ace.registeruser.vo.update.ChangePrimaryPhoneNumber;
import com.ace.registeruser.vo.update.PhoneUpdateResponse;
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
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @Operation(summary = "Fetch phone details",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER,
                            name = "userName",
                            description = "user Name",
                            required = true,
                            example = "sunil",
                            schema = @Schema(type = "string"))})

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetch phone details successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetUserPhoneResponse.class)) })
    })

    @GetMapping(value = "user/fetch/phone")
    public ResponseEntity<GetUserPhoneResponse> getPhone(@RequestAttribute("userSession") Object userSession) {

        UserSession userSessionInfo=(UserSession) userSession;
        GetUserPhoneResponse getPhoneResponse = phoneService.getPhone(userSessionInfo.getUserName(),userSessionInfo);

        return new ResponseEntity<GetUserPhoneResponse>(getPhoneResponse, HttpStatus.OK);
    }

    @Operation(summary = "Add secondary phone number",
            description = "secondary phone number should always new phone number i.e not present as active phone in DB",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER,
                            name = "userName",
                            description = "user Name",
                            required = true,
                            example = "sunil",
                            schema = @Schema(type = "string"))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add secondary phone number successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PhoneUpdateResponse.class)) })
    })


    @PostMapping(value="/user/phone/addSecondary")
    public ResponseEntity<PhoneUpdateResponse> addNewPhoneNumberAsSecondary(
            @RequestBody @Valid AddSecondaryPhoneNumber addSecondaryPhoneNumber
            ,@RequestAttribute("userSession") Object userSession){

        UserSession userSessionInfo=(UserSession) userSession;
        phoneService.addAddSecondaryPhoneNumber(addSecondaryPhoneNumber,userSessionInfo);

        return new ResponseEntity<PhoneUpdateResponse>(
                PhoneUpdateResponse
                        .builder()
                        .phoneNumberIsUpdated(true)
                        .build(),
                HttpStatus.OK);
    }

    @Operation(summary = "Add New Primary PhoneNumber",
            description = "new primary phoneNumber is either users' existing phone number or an new phone number",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER,
                            name = "userName",
                            description = "user Name",
                            required = true,
                            example = "sunil",
                            schema = @Schema(type = "string"))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add New Primary PhoneNumber successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PhoneUpdateResponse.class)) })
    })

    @PostMapping(value="/user/phone/addNewPrimaryPhoneNumber")
    public ResponseEntity<PhoneUpdateResponse> addNewPrimaryPhoneNumber(@RequestBody @Valid ChangePrimaryPhoneNumber changePrimaryPhoneNumber, @RequestAttribute("userSession") Object userSession){

        UserSession userSessionInfo=(UserSession) userSession;

        phoneService.addNewPrimaryPhoneNumber(changePrimaryPhoneNumber,userSessionInfo);

        return new ResponseEntity<PhoneUpdateResponse>(
                PhoneUpdateResponse
                        .builder()
                        .phoneNumberIsUpdated(true)
                        .build(),
                HttpStatus.OK);
    }

    @Operation(summary = "Expire user phone",
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
                            schema = @Schema(implementation = PhoneExpireResponse.class)) })
    })
    @DeleteMapping(value="user/phone/expire")
    public ResponseEntity<PhoneExpireResponse> expirePhone(@RequestBody @Valid PhoneExpireRequest expirePhoneRequest,
                                                           @RequestAttribute("userSession") Object userSession){

        UserSession userSessionInfo=(UserSession)userSession;

        phoneService.expirePhone(expirePhoneRequest,userSessionInfo);

        return new ResponseEntity<PhoneExpireResponse>(
                PhoneExpireResponse
                        .builder()
                        .phoneNumberIsExpired(true)
                        .build(),
                HttpStatus.OK);
    }

}
