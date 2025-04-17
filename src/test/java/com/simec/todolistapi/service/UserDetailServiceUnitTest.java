package com.simec.todolistapi.service;

import com.simec.todolistapi.dao.UserDao;
import com.simec.todolistapi.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailServiceUnitTest {

    private UserDetailsService userDetailsService;
    private UserDao userDaoMock;
    private static final String EMAIL = "tester@mail.com";

    @BeforeEach
    void setUp() {
        userDaoMock = Mockito.mock(UserDao.class);
        userDetailsService = new UserDetailServiceImpl(userDaoMock);
    }

    @Test
    void whenLoadUserByUsernameThenUserExists() {
        String username = "name";
        String password = "password";
        Mockito.when(userDaoMock.findByEmail(EMAIL)).thenReturn(
                Optional.of(new User.Builder()
                        .withId(1)
                        .withEmail(EMAIL)
                        .withUsername(username)
                        .withPassword(password)
                        .build()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(EMAIL);
        assertEquals(userDetails,
                new org.springframework.security.core.userdetails.User(
                        EMAIL,
                        password,
                        List.of()));
    }

    @Test
    void whenLoadUserByUsernameThenUserDoesNotExists() {
        Mockito.when(userDaoMock.findByEmail(EMAIL)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(EMAIL));
    }
}