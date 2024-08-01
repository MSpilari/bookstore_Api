package com.example.bookstore.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.controllers.dto.AddUserDTO;
import com.example.bookstore.services.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AddUserDTO userInput) {
        var token = this.tokenService.getToken(userInput);

        return ResponseEntity.ok(token);
    }

}
