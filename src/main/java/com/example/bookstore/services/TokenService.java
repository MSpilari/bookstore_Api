package com.example.bookstore.services;

import java.time.Instant;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.bookstore.controllers.dto.AddUserDTO;
import com.example.bookstore.repositories.UserRepository;

@Service
public class TokenService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Map<String, Object> getToken(AddUserDTO userInput) {
        var userExists = this.userRepository.findByEmail(userInput.email())
                .filter(user -> user.passwordVerifier(userInput, bCryptPasswordEncoder))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or password invalid."));

        var now = Instant.now();
        var expiresIn = 300L;

        var scope = userExists.getRole();

        var claims = JwtClaimsSet.builder()
                .issuer("bookstore_API")
                .subject(userExists.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scope)
                .build();

        var jwtToken = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return Map.of("token", jwtToken, "expiredAt", expiresIn);
    }
}
