package com.lucas.restspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.restspringboot.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
