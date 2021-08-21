package com.ace.registeruser.controller;

import com.ace.registeruser.entity.UserSession;
import com.ace.registeruser.vo.expire.EmailExpireResponse;
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
import org.springframework.web.bind.annotation.*;

import com.ace.registeruser.service.FetchUserDetailsService;
import com.ace.registeruser.vo.get.GetEmailResponse;
import com.ace.registeruser.vo.get.GetUserCompletInfoResponse;
import com.ace.registeruser.vo.get.GetUserPhoneResponse;

@RestController
//@CrossOrigin(origins="http://localhost:4200")
public class FetchUserDetailsController {

	@Autowired
	FetchUserDetailsService fetchUserDetailsService;

	@Operation(description = "Fetch complete user details",
			parameters = {
					@Parameter(in = ParameterIn.HEADER,
							name = "userName",
							description = "user Name",
							required = true,
							schema = @Schema(type = "string"))})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = " Fetch complete user details successfully",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = GetUserCompletInfoResponse.class)) })
	})
	@GetMapping(value = "user/fetch/complete")
	public ResponseEntity<GetUserCompletInfoResponse> getUserCompletInformation(@RequestAttribute("userSession") Object userSession) {

		UserSession userSessionInfo=(UserSession) userSession;
		GetUserCompletInfoResponse userCompletInfoResponse = fetchUserDetailsService.getUserCompleteInfo(userSessionInfo.getUserName(),userSessionInfo);

		return new ResponseEntity<GetUserCompletInfoResponse>(userCompletInfoResponse, HttpStatus.OK);
	}

}
