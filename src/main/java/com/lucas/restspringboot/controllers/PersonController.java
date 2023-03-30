package com.lucas.restspringboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.restspringboot.data.dto.v1.PersonDTO;
import com.lucas.restspringboot.data.dto.v2.PersonDTOV2;
import com.lucas.restspringboot.services.PersonService;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping(value = "/{id}")
    public PersonDTO findById(@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping()
    public List<PersonDTO> findAll() throws Exception {
        return service.findAll();
    }

    @PostMapping
    public PersonDTO create(@RequestBody PersonDTO person) throws Exception {
        return service.create(person);
    }

    @PostMapping(value = "/v2")
    public PersonDTOV2 createV2(@RequestBody PersonDTOV2 person) throws Exception {
        return service.createV2(person);
    }

    @PutMapping(value = "/{id}")
    public PersonDTO update(@PathVariable(value = "id") Long id, @RequestBody PersonDTO person) throws Exception {
        return service.update(id, person);
    }
}
