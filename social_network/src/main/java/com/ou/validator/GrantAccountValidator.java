package com.ou.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ou.pojo.Account;
import com.ou.pojo.User;
import lombok.Setter;

@Component
@Setter
public class GrantAccountValidator implements Validator{
    @Autowired
    private javax.validation.Validator beanValidators;

    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.isAssignableFrom(clazz) ||
            User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Set<ConstraintViolation<Object>> constraintViolations
            = beanValidators.validate(target);
        constraintViolations.forEach(cs -> {
            errors.reject(
                cs.getMessageTemplate(),
                cs.getMessage()
            );
        });
    }
}
