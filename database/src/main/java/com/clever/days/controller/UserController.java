package com.clever.days.controller;

import com.clever.days.entity.UserEO;
import com.clever.days.exception.ResourceNotFoundException;
import com.clever.days.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<UserEO> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserEO> getUserById(@PathVariable(value = "id")
                                                    Long id) throws ResourceNotFoundException {

        UserEO user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("User not found for this id :: " + id));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users")
    public UserEO createUser(@RequestBody UserEO user) {
        return userRepository.save(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserEO> updateUser(@PathVariable(value = "id")
                                                   Long id, @RequestBody UserEO userDto)
            throws ResourceNotFoundException {

        UserEO user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("User not found for this id :: " + id));

//        user.setEmailId(userDto.getEmailId());
//        user.setLastName(userDto.getLastName());
//        user.setFirstName(userDto.getFirstName());
//        user.setId(id);
        final UserEO updateUser = userRepository.save(user);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id")
                                                   Long id) throws ResourceNotFoundException {
        UserEO user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("User not found for this id :: " + id));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
