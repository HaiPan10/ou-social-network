package com.ou.validator;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ou.pojo.Account;
import com.ou.pojo.User;
import com.ou.pojo.UserStudent;

import lombok.Setter;

@Component
@Setter
public class WebAppValidator implements Validator{
    @Autowired
    private javax.validation.Validator beanValidators;

    @Autowired
    private PassValidator passValidator;

    private Set<Validator> springValidators = new HashSet<>();

    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.isAssignableFrom(clazz) ||
            User.class.isAssignableFrom(clazz) ||
            UserStudent.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        springValidators.add(passValidator);
        Set<ConstraintViolation<Object>> constraintViolations
            = beanValidators.validate(target);
        constraintViolations.forEach(cs -> {
            errors.reject(
                cs.getMessageTemplate(),
                cs.getMessage()
            );
        });
        springValidators.forEach(sv -> {
            sv.validate(target, errors);
        });
    }
    
}
