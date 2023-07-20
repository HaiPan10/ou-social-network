package com.ou.validator;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;

import com.cloudinary.provisioning.Account;

import lombok.Setter;

@Component
@Setter
public class WebAppValidator implements Validator {
    // javax bean validation
    @Autowired
    private javax.validation.Validator beanValidator;

    // Spring validator
    private Set<Validator> springValidators = new HashSet<>();

    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Set<ConstraintViolation<Object>> violations = beanValidator.validate(target);
        // Bean validation process
        violations.forEach(v -> {
            errors.rejectValue(
                v.getPropertyPath().toString(),
                v.getMessageTemplate(),
                v.getMessage()
            );
        });

        // Spring validation
        springValidators.forEach(s -> {
            s.validate(target, errors);
        });
    }
    
}
