package com.ace.registeruser.validation;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.ace.registeruser.entity.EmailDetails;
import com.ace.registeruser.repository.EmailRepositiry;

public class IsEmailExistInDBValidator implements ConstraintValidator<IsEmailExistInDB, String> {

	@Autowired
	private EmailRepositiry emailRepositiry;

	String message = "email alrady present";

	@Override
	public void initialize(IsEmailExistInDB email) {

		this.message = email.message();
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {

		Example<EmailDetails> example = Example.of(EmailDetails.builder().emailAddress(email)
			    .isEmailActive("Y")
				.build());
		Optional<EmailDetails> emailDetails = emailRepositiry.findOne(example);
		if (!emailDetails.isPresent()) {
			return true;
		}
		return false;
	}

}
