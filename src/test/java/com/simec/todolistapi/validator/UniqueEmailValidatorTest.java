package com.simec.todolistapi.validator;

import com.simec.todolistapi.dao.UserDao;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UniqueEmailValidatorTest {
    private final ConstraintValidatorContext contextMock = Mockito.mock(ConstraintValidatorContext.class);
    private UserDao userDaoMock;
    private UniqueEmailValidator uniqueEmailValidator;

    @BeforeEach
    void setup() {
        userDaoMock = Mockito.mock(UserDao.class);
        uniqueEmailValidator = new UniqueEmailValidator(userDaoMock);
    }

    @Test
    void emailIsNullWhenValidatedThenIsNotValid() {
        assertFalse(uniqueEmailValidator.isValid(null, contextMock));
    }

    @Test
    void emailIsNullWhenValidatedThenIsDaoNotUsed() {
        uniqueEmailValidator.isValid(null, contextMock);
        Mockito.verify(userDaoMock, Mockito.never()).isEmailUnique(Mockito.anyString());
    }

    @Test
    void emailIsAlreadyUsedWhenValidatedThenIsNotValid() {
        String email = "test@test.com";
        Mockito.when(userDaoMock.isEmailUnique(email)).thenReturn(false);
        assertFalse(uniqueEmailValidator.isValid(email, contextMock));
    }

    @Test
    void emailIsNotUsedWhenValidatedThenIsValid() {
        String email = "test@test.com";
        Mockito.when(userDaoMock.isEmailUnique(email)).thenReturn(true);
        assertTrue(uniqueEmailValidator.isValid(email, contextMock));
    }
}