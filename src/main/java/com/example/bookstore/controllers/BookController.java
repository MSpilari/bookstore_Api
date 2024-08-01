package com.example.bookstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookstore.entities.BookEntity;
import com.example.bookstore.services.BookService;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/book")
    @PreAuthorize("hasAuthority('SCOPE_basic')")
    public BookEntity addBook(@RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("imgUrl") MultipartFile imgUrl,
            JwtAuthenticationToken token) {
        var res = this.bookService.addBook(name, type, imgUrl, token);
        return res;
    }

}
