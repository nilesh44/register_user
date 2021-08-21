package com.ace.registeruser.vo.create;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.ace.registeruser.entity.EmailDetails;
import com.ace.registeruser.validation.IsEmailExistInDB;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEmail {
	
	@NotEmpty(message="please provide email address")
	@Email(message="please provide email in format abc@xyz.com")
	@IsEmailExistInDB(message="email already present")
	private String emailAddress;
	
	private String isActive;

	private String userPreference;
	
	public static List<UserEmail>from(List<EmailDetails> emailDetails){
		return emailDetails
				.parallelStream()
				.map(email->populateEmail(email))
				.collect(Collectors.toList());
	}

	private static UserEmail populateEmail(EmailDetails email){
		return UserEmail
				.builder()
				.emailAddress(email.getEmailAddress())
				.isActive(email.getIsEmailActive())
				.userPreference(getUserPreference(email.getUserPreferenceCode()))
				.build();
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

}
