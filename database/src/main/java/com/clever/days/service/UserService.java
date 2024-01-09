package com.clever.days.service;

import com.clever.days.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User createUser(long tgId, String name);

    User getUserById(UUID id);

    User getUserByTgId(long id);

    List<User> getAllUsers();
}
