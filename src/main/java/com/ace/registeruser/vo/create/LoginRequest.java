package com.ace.registeruser.vo.create;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	@Schema(example = "sunil",description = "user name")
	@NotEmpty(message="please provide userNmae")
	private String userName;

	@Schema(example = "sunil@123",description = "user password")
	@NotEmpty(message="please provide password")
	private String password;
}
