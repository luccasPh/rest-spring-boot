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

import com.lucas.restspringboot.models.Person;
import com.lucas.restspringboot.models.dto.PersonDTO;
import com.lucas.restspringboot.services.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping(value = "/{id}")
    public Person findById(@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping()
    public List<Person> findAll() throws Exception {
        return service.findAll();
    }

    @PostMapping
    public Person create(@RequestBody PersonDTO person) throws Exception {
        return service.create(person);
    }

    @PutMapping(value = "/{id}")
    public Person update(@PathVariable(value = "id") Long id, @RequestBody PersonDTO person) throws Exception {
        return service.update(id, person);
    }
}
