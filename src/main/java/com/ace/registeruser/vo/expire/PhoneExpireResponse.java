package com.ace.registeruser.vo.expire;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneExpireResponse {
	
	private boolean phoneNumberIsExpired;

}
