package com.clever.days.service;

import com.clever.days.model.User;

import java.util.UUID;

public interface UserService {

    User getUserById(UUID id);

    User createUser(Long tgId, String name);
}
