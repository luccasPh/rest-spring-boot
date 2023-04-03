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

import com.lucas.restspringboot.data.dto.v1.PersonDTO;
import com.lucas.restspringboot.data.dto.v2.PersonDTOV2;
import com.lucas.restspringboot.exceptions.ExceptionResponse;
import com.lucas.restspringboot.services.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/person")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Find a Person by id", description = "Find a Person by id", tags = { "People" }, responses = {
            @ApiResponse(description = "Sucess", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonDTO.class))),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))) })
    public PersonDTO findById(@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @Operation(summary = "Find all People", description = "Find all People", tags = { "People" }, responses = {
            @ApiResponse(description = "Sucess", responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))) }) })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<PersonDTO> findAll() throws Exception {
        return service.findAll();
    }

    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Create a new Person", description = "Create a new Person", tags = { "People" }, responses = {
            @ApiResponse(description = "Sucess", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonDTO.class))) })
    public PersonDTO create(@RequestBody PersonDTO person) throws Exception {
        return service.create(person);
    }

    @PostMapping(value = "/v2", produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Create a new Person V2", description = "Create a new Person V2", tags = {
            "People" }, responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonDTOV2.class))) })
    public PersonDTOV2 createV2(@RequestBody PersonDTOV2 person) throws Exception {
        return service.createV2(person);
    }

    @PutMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Update a Person by id", description = "Update a Person by id", tags = {
            "People" }, responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))) })
    public PersonDTO update(@PathVariable(value = "id") Long id, @RequestBody PersonDTO person) throws Exception {
        return service.update(id, person);
    }
}
