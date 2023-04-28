package com.lucas.restspringboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @CrossOrigin(origins = "http://localhost:8080")
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
    public ResponseEntity<Page<PersonDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "12") Integer limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) throws Exception {
        var sort = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sort, "firstName"));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @CrossOrigin(origins = { "http://localhost:8080", "https://lucas.com.br" })
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

    @PatchMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Disable a Person by id", description = "Disable a Person by id", tags = {
            "People" }, responses = {
                    @ApiResponse(description = "Sucess", responseCode = "204"),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))) })
    public ResponseEntity<Object> disablePerson(@PathVariable(value = "id") Long id)
            throws Exception {
        service.disablePerson(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Find People by first name", description = "Find People by first name", tags = {
            "People" }, responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200", content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))) }) })
    @GetMapping(value = "/find/name/{firstName}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Page<PersonDTO>> findByName(
            @PathVariable(value = "firstName") String firstName,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "12") Integer limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) throws Exception {
        var sort = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sort, "firstName"));
        return ResponseEntity.ok(service.findPersonsByName(firstName, pageable));
    }
}
