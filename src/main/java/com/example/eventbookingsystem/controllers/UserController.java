package com.example.eventbookingsystem.controllers;

import com.example.eventbookingsystem.models.Users;
import com.example.eventbookingsystem.response.MessageResponse;
import com.example.eventbookingsystem.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody Users user) {
        Users registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponse> loginUser(@RequestParam String username, @RequestParam String password) {
        Users authenticatedUser = userService.authenticateUser(username, password);
        return ResponseEntity.ok().body(new MessageResponse("Login Successfully",authenticatedUser,false));
    }
}