package com.example.demo.validator;


import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;
import java.util.Objects;

@Component
@AllArgsConstructor
public class UserIDExistingValidator implements ConstraintValidator<UserIDExisting, Integer> {
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Override
    public boolean isValid(Integer userId, ConstraintValidatorContext context) {
        boolean valid = Objects.isNull(userId) || this.userRepository.findById(userId).isPresent();
        if(!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.messageSource.getMessage("user.id.not.exist", new Integer[]{userId}, Locale.ENGLISH))
                    .addConstraintViolation();
        }
        return valid;
    }
}