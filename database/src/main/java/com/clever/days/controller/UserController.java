package com.clever.days.controller;

import com.clever.days.exception.ResourceNotFoundException;
import com.clever.days.model.User;
import com.clever.days.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestParam long tgId, @RequestParam String name) {
        var createdUser = userService.createUser(tgId, name);

        return createdUser == null
                ? ResponseEntity.status(HttpStatus.CONFLICT).build()
                : ResponseEntity.ok(createdUser);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/tg/{tgId}")
    public ResponseEntity<User> getUserByTgId(@PathVariable(value = "tgId") long id) {
        return ResponseEntity.ok().body(userService.getUserByTgId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") UUID id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }










//    @PutMapping("/users/{id}")
//    public ResponseEntity<UserEO> updateUser(@PathVariable(value = "id")
//                                                   Long id, @RequestBody UserEO userDto)
//            throws ResourceNotFoundException {
//
//        UserEO user = userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException
//                        ("User not found for this id :: " + id));
//
//
//        final UserEO updateUser = userRepository.save(user);
//        return ResponseEntity.ok(updateUser);
//    }

//    @DeleteMapping("/users/{id}")
//    public Map<String, Boolean> deleteUser(@PathVariable(value = "id")
//                                                   Long id) throws ResourceNotFoundException {
//        UserEO user = userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException
//                        ("User not found for this id :: " + id));
//
//        userRepository.delete(user);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("deleted", Boolean.TRUE);
//        return response;
//    }
}
