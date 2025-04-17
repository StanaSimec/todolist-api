package com.simec.todolistapi.dao;

import com.simec.todolistapi.entity.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findByEmail(String email);

    User create(User user);

    boolean isEmailUnique(String email);

    boolean isEmailUniqueIgnoreUserId(String email, int userId);
}
