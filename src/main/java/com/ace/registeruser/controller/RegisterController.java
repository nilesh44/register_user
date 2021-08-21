package com.ace.registeruser.controller;

import com.ace.registeruser.vo.get.GetUserCompletInfoResponse;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ace.registeruser.service.RegisterUserService;
import com.ace.registeruser.vo.create.RegisterUserRequest;
import com.ace.registeruser.vo.create.CreateUserResponse;

@RestController
@Validated
//@CrossOrigin(origins="http://localhost:4200")
public class RegisterController {
	
	@Autowired
	private RegisterUserService registerUserService;

	@Operation(description = "Register user details",
			parameters = {
					@Parameter(in = ParameterIn.HEADER,
							name = "userName",
							description = "user Name",
							required = true,
							schema = @Schema(type = "string"))})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = " User Registered successfully",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = CreateUserResponse.class)) })
	})

	@PostMapping(path="/user/register")
	public ResponseEntity<CreateUserResponse> createUser( @RequestBody @Validated RegisterUserRequest registerUserRequest) {
		
		return new ResponseEntity<CreateUserResponse>(registerUserService.createUser(registerUserRequest), HttpStatus.OK);
		
	}

}

