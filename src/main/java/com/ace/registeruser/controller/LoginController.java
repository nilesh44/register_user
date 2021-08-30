package com.ace.registeruser.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ace.registeruser.service.LoginService;
import com.ace.registeruser.vo.create.LoginRequest;
import com.ace.registeruser.vo.create.LoginResponse;
import com.ace.registeruser.vo.create.LogoutResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
//@CrossOrigin(origins="http://localhost:4200")
public class LoginController {
	
	@Autowired
	LoginService loginService;

	@Operation(summary = "Login User")

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "login successfully",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = LoginResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "invalid user credential",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Error.class))})
			})

	@PostMapping(value="user/login")
	public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest){
		loginService.logIn(loginRequest.getUserName(), loginRequest.getPassword());
		return new ResponseEntity<LoginResponse>(LoginResponse.builder().isUserLogedIn(true).build(), HttpStatus.OK);
	}

	@Operation(summary = "logOut User",
			parameters = {
					@Parameter(in = ParameterIn.HEADER,
							name = "userName",
							description = "user Name",
							required = true,
							example = "sunil",
							schema = @Schema(type = "string"))} )

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "logOut successfully",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = LogoutResponse.class)) })
	})

	@PostMapping(value="user/logout")
	public ResponseEntity<LogoutResponse> logout( @RequestHeader("userName") String userName){
		loginService.logOut(userName);
		return new ResponseEntity<LogoutResponse>(LogoutResponse.builder().isUserLoggedOut(true).build(), HttpStatus.OK);
	}
}
