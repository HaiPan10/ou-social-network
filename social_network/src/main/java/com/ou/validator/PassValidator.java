package com.ou.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ou.pojo.Account;

/**
 *
 * @author HaiPhan
 */
@Component
public class PassValidator implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target instanceof Account){
            Account account = (Account) target;
            if(!account.getPassword().equals(account.getConfirmPassword())){
                errors.reject("user.password.notMatchMsg", "Password don't match");
            }
        }
    }
    
}
