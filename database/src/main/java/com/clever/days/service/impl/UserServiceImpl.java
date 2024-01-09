package com.clever.days.service.impl;

import com.clever.days.entity.UserEO;
import com.clever.days.exception.NotFoundException;
import com.clever.days.mapper.DomUserMapper;
import com.clever.days.model.User;
import com.clever.days.repository.UserRepository;
import com.clever.days.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DomUserMapper domUserMapper;

    @Override
    public User createUser(long tgId, String name) {
        if (userRepository.findByTgId(tgId).isEmpty()) {
            var uuid = UUID.randomUUID();
            var createdUser = userRepository.save(new UserEO(uuid, tgId, name));
            return domUserMapper.toDto(createdUser);
        }

        return null;
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).map(domUserMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format("User with id '%s' not found", id)));
    }

    @Override
    public User getUserByTgId(long id) {
        var userEO = userRepository.findByTgId(id);

        return userEO.map(eo -> domUserMapper.toDto(eo)).orElse(null);
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                .map(domUserMapper::toDto)
                .collect(Collectors.toList());
    }
}
