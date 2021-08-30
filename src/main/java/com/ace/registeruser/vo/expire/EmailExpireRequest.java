package com.ace.registeruser.vo.expire;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailExpireRequest {

	@NotEmpty(message="please provide email address")
	@Email(message="please provide email in formate abc@xyz.com")
	private String emailAddress;
	
}
