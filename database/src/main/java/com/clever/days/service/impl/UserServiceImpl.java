package com.clever.days.service.impl;

import com.clever.days.entity.UserEO;
import com.clever.days.mapper.DomUserMapper;
import com.clever.days.model.User;
import com.clever.days.repository.UserRepository;
import com.clever.days.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private DomUserMapper domUserMapper;

    @Override
    public User getUserById(UUID id) {
//        var userEO = userRepository.findById(id).orElseThrow(() ->
//                new NotFoundException(String.format("car with id '%s' not found", id)));
//
//        // other logic
//
//        return domUserMapper.map(userEO);
        return null;

    }


    @Override
    public void createUser(long tgId, String name) {
        var createdUser = userRepository.save(new UserEO(UUID.randomUUID(), tgId, name));
//        return domUserMapper.map(createdUser);
    }
}
