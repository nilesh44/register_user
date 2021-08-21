package com.ace.registeruser.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneAlradyPresentInDBValidator.class)
@Documented
public @interface PhoneAlradyPresentInDB {

    String message() default "phone already present";
	
	boolean nullable() default true;

	boolean empty() default true;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
