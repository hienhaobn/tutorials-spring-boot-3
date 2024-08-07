package com.hami.identity_service.controller;

import com.hami.identity_service.dto.request.UserCreationRequest;
import com.hami.identity_service.dto.request.UserUpdateRequest;
import com.hami.identity_service.entity.User;
import com.hami.identity_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @PostMapping
    User createUser(@RequestBody @Valid UserCreationRequest request) {
        return userService.create(request);
    }

    @GetMapping
    List<User> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") String userId) {
        return userService.getOne(userId);
    }

    @PutMapping("/{userId}")
    User updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request) {
        return userService.update(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable("userId") String userId) {
        userService.delete(userId);
        return "User has been deleted";
    }
}
