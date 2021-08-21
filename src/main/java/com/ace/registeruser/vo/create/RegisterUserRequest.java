package com.ace.registeruser.vo.create;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.ace.registeruser.validation.IsUserExistInDB;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {

	@NotEmpty(message = "please provide userNmae")
	@IsUserExistInDB(message = "username alrady present")
	private String userName;

	@NotEmpty(message = "please provide password")
	private String password;

	private UserPersonalInfo userPersonalInfo;

	@Valid
	private UserEmail userEmails;
	
	@Valid
	private UserPhone userPhones;

}
