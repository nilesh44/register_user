package com.ace.registeruser.vo.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneUpdateResponse {
	
	private boolean phoneNumberIsUpdated;

}
