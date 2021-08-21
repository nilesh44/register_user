package com.ace.registeruser.vo.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConstraintVoilationError {
	
	private Object parameter;
	
	private Object invalidValue;
	
	private String message;

}
