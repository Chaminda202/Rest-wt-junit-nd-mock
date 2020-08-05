package com.example.demo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = UserIDExistingValidator.class)
public @interface UserIDExisting {
    String message() default "{user.id.not.exist}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}