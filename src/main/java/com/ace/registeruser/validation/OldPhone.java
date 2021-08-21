package com.ace.registeruser.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OldPhoneValidator.class)
@Documented
public @interface OldPhone {

    public String message() default "old phone is invalid";

    boolean nullable() default true;

    boolean empty() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
