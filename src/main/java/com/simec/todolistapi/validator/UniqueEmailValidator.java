package com.simec.todolistapi.validator;

import com.simec.todolistapi.dao.UserDao;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserDao userDao;

    @Autowired
    public UniqueEmailValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true;
        }
        return userDao.isEmailUnique(email);
    }
}
