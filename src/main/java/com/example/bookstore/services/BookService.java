package com.example.bookstore.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.bookstore.entities.BookEntity;
import com.example.bookstore.repositories.BookRepository;
import com.example.bookstore.repositories.UserRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${CLOUDINARY_URL}")
    private String cloudinaryUrl;

    public BookEntity addBook(String name, String type, MultipartFile imgUrl, JwtAuthenticationToken token) {

        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);

        try {
            // Converta MultipartFile para File
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + imgUrl.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(imgUrl.getBytes());
            fos.close();

            var pic = cloudinary.uploader().upload(convFile, ObjectUtils.asMap("folder", "/bookCovers/"));

            var userOpt = this.userRepository.findById(UUID.fromString(token.getName()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found !"));

            var book = new BookEntity();
            book.setName(name);
            book.setType(type);
            book.setUser(userOpt);
            book.setImgUrl(pic.get("url").toString());

            return this.bookRepository.save(book);

        } catch (IOException e) {
            new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Image upload failed ! ");
            return null;
        }

    }
}
