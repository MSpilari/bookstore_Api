package com.example.bookstore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.controllers.dto.AddUserDTO;
import com.example.bookstore.entities.UserEntity;
import com.example.bookstore.services.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> ok() {
        var allUsers = this.userService.listUsers();
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserEntity> addUser(@RequestBody AddUserDTO userInput) {
        var newUser = this.userService.addUser(userInput);

        return ResponseEntity.ok(newUser);
    }

}
