package com.ace.registeruser.vo.update;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePrimaryPhoneNumber {

	@Schema(description = "new Primary PhoneNumber of contact",example = "6767676767",required = true)
	@NotEmpty(message = "newPrimaryPhoneNumber must not blank")
	@Digits(fraction = 0, integer = 10, message = "newPrimaryPhoneNumber should be number")
	@Size(min = 10, max = 10, message = "please provide 10 digit newPrimaryPhoneNumber")
	private String newPrimaryPhoneNumber;

}
