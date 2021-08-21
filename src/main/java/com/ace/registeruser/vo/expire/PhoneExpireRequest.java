package com.ace.registeruser.vo.expire;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneExpireRequest {
	
	@NotEmpty(message = "please provide phone number")
	@Digits(fraction = 0, integer = 10, message = "phone number should be number")
	@Size(min = 10, max = 10, message = "please provide 10 digit phone number")
	private String phoneNumber;

}
