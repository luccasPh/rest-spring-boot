package com.lucas.restspringboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.restspringboot.data.dto.v1.BookDTO;
import com.lucas.restspringboot.exceptions.ExceptionResponse;
import com.lucas.restspringboot.services.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/book")
@Tag(name = "Books", description = "Endpoints for Managing Books")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Find a Book by id", description = "Find a Book by id", tags = { "Books" }, responses = {
            @ApiResponse(description = "Sucess", responseCode = "200", content = @Content(schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))) })
    public BookDTO findById(@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @Operation(summary = "Find all Books", description = "Find all Books", tags = { "Books" }, responses = {
            @ApiResponse(description = "Sucess", responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))) }) })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<BookDTO> findAll() throws Exception {
        return service.findAll();
    }

    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Create a new Book", description = "Create a new Book", tags = { "Books" }, responses = {
            @ApiResponse(description = "Sucess", responseCode = "200", content = @Content(schema = @Schema(implementation = BookDTO.class))) })
    public BookDTO create(@RequestBody BookDTO book) throws Exception {
        return service.create(book);
    }

    @PutMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Update a Book by id", description = "Update a Book by id", tags = {
            "Books" }, responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200", content = @Content(schema = @Schema(implementation = BookDTO.class))),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))) })
    public BookDTO update(@PathVariable(value = "id") Long id, @RequestBody BookDTO book) throws Exception {
        return service.update(id, book);
    }
}
