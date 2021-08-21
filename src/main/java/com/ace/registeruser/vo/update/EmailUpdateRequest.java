package com.ace.registeruser.vo.update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.ace.registeruser.validation.IsEmailExistInDB;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailUpdateRequest {

	private String oldEmailAddress;

	@NotEmpty(message = "please provide email address")
	@Email(message = "please provide email in formate abc@xyz.com")
	@IsEmailExistInDB(message = "email alrady present")
	private String newEmailAddress;
}
