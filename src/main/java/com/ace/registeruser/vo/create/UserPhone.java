package com.ace.registeruser.vo.create;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.ace.registeruser.entity.MobileDetails;
import com.ace.registeruser.validation.PhoneAlradyPresentInDB;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPhone {

	@NotEmpty(message = "please provide phone number")
	@Digits(fraction = 0, integer = 10, message = "phone number should be number")
	@Size(min = 10, max = 10, message = "please provide 10 digit phone number")
	@PhoneAlradyPresentInDB
	private String phoneNumber;

	private String isActive;

	private String userPreference;

	public static List<UserPhone> from(List<MobileDetails> phoneDetails) {
		return phoneDetails
				.stream()
				.map(phone -> populateUserPhone( phone))
				.collect(Collectors.toList());
	}

   private static String getUserPreference(String userPreferenceCode){

		if(userPreferenceCode.equals("01")) {
			return "primary";
		}
	   if(userPreferenceCode.equals("02")){
		return "secondary";
	    }
	return"unknown";
}

private static UserPhone populateUserPhone(MobileDetails phone){

	return UserPhone
			.builder()
			.phoneNumber(phone.getPhoneNumber())
			.isActive(phone.getIsPhoneActive())
			.userPreference(getUserPreference(phone.getUserPreferenceCode()))
			.build();
}

}
