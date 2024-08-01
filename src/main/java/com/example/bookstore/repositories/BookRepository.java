package com.example.bookstore.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookstore.entities.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, UUID> {

}
