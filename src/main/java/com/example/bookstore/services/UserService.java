package com.example.bookstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.bookstore.controllers.dto.AddUserDTO;
import com.example.bookstore.entities.UserEntity;
import com.example.bookstore.enums.RolesEnum;
import com.example.bookstore.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserEntity addUser(AddUserDTO userInput) {

        var emailExists = this.userRepository.findByEmail(userInput.email());

        if (emailExists.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists.");
        }
        var newUser = new UserEntity();

        newUser.setEmail(userInput.email());
        newUser.setPassword(bCryptPasswordEncoder.encode(userInput.password()));
        newUser.setRole(String.valueOf(RolesEnum.basic));

        return this.userRepository.save(newUser);
    }

    public List<UserEntity> listUsers() {
        return this.userRepository.findAll();
    }

}
