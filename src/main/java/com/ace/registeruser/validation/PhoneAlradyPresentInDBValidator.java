package com.ace.registeruser.validation;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.ace.registeruser.entity.PhoneDetails;
import com.ace.registeruser.repository.PhoneRepository;

public class PhoneAlradyPresentInDBValidator implements ConstraintValidator<PhoneAlradyPresentInDB, String> {

	@Autowired
	private PhoneRepository phoneRepository;

	@Override
	public void initialize(PhoneAlradyPresentInDB phone) {
	}

	@Override
	public boolean isValid(String phone, ConstraintValidatorContext context) {


		if(StringUtils.isBlank(phone) ) {
	return false;
}
		Example<PhoneDetails> example = Example.of(PhoneDetails.builder().phoneNumber(phone)
			    .isPhoneActive("Y")
				.build());
		Optional<PhoneDetails> phoneDetails = phoneRepository.findOne(example);
		if (!phoneDetails.isPresent()) {
			return true;
		}
		context.buildConstraintViolationWithTemplate("phone number already present in DB").addConstraintViolation();
		return false;
	}

}