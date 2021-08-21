package com.ace.registeruser.validation;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.ace.registeruser.entity.UserCredentials;
import com.ace.registeruser.repository.UserCredentialRepository;

public class IsUserExistInDBValidator implements ConstraintValidator<IsUserExistInDB, String> {

	@Autowired
	private UserCredentialRepository userCredentialRepository;

	String message = "users alrady present";

	@Override
	public void initialize(IsUserExistInDB isUserExistInDB) {

		this.message = isUserExistInDB.message();
	}

	@Override
	public boolean isValid(String userName, ConstraintValidatorContext context) {

		Example<UserCredentials> example = Example.of(UserCredentials.builder().username(userName)
			    .isUserCredentialActive("Y")
				.build());
		Optional<UserCredentials> phoneDetails = userCredentialRepository.findOne(example);
		if (!phoneDetails.isPresent()) {
			return true;
		}
		return false;
	}
}
