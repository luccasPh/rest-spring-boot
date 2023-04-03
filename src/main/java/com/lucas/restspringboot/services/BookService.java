package com.lucas.restspringboot.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.restspringboot.data.dto.v1.BookDTO;
import com.lucas.restspringboot.exceptions.NotFoundBusinessException;
import com.lucas.restspringboot.models.Book;
import com.lucas.restspringboot.repositories.BookRepository;
import com.lucas.restspringboot.shared.mapper.ObjectMapper;

@Service
public class BookService {

    @Autowired
    BookRepository repository;

    private Logger logger = Logger.getLogger(BookService.class.getName());

    public BookDTO findById(Long id) {
        logger.info(String.format("Finding book by id: %s", id));
        Book book = repository.findById(id)
                .orElseThrow(() -> new NotFoundBusinessException(
                        String.format("Not found book with id: %s", id)));

        return ObjectMapper.parseObject(book, BookDTO.class);
    }

    public List<BookDTO> findAll() {
        logger.info("Finding all books");

        return ObjectMapper.parseListObjects(repository.findAll(), BookDTO.class);
    }

    public BookDTO create(BookDTO BookDTO) {
        logger.info(String.format("Create a book: %s", BookDTO.toString()));

        Book book = ObjectMapper.parseObject(BookDTO, Book.class);

        return ObjectMapper.parseObject(repository.save(book), BookDTO.class);
    }

    public BookDTO update(Long id, BookDTO BookDTO) {
        logger.info(String.format("Update a book id: %s, %s ", id, BookDTO.toString()));

        Book book = repository.findById(id)
                .orElseThrow(() -> new NotFoundBusinessException(
                        String.format("Not found book with id: %s", id)));
        book.setAuthor(BookDTO.getAuthor());
        book.setLaunchDate(BookDTO.getLaunchDate());
        book.setPrice(BookDTO.getPrice());
        book.setTitle(BookDTO.getTitle());

        return ObjectMapper.parseObject(repository.save(book), BookDTO.class);
    }
}
