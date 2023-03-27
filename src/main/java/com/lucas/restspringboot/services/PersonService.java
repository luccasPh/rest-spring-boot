package com.lucas.restspringboot.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.restspringboot.exceptions.NotFoundBusinessException;
import com.lucas.restspringboot.models.Person;
import com.lucas.restspringboot.models.dto.PersonDTO;
import com.lucas.restspringboot.repositories.PersonRepository;

@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person findById(Long id) {
        logger.info(String.format("Finding person by id: %s", id));

        return repository.findById(id)
                .orElseThrow(() -> new NotFoundBusinessException(
                        String.format("Not found person with id: %s", id)));
    }

    public List<Person> findAll() {
        logger.info("Finding all persons");

        return repository.findAll();
    }

    public Person create(PersonDTO personDto) {
        logger.info(String.format("Create a person: %s", personDto.toString()));

        Person person = personDto.toModel();

        return repository.save(person);
    }

    public Person update(Long id, PersonDTO personDto) {
        logger.info(String.format("Update a person id: %s, %s ", id, personDto.toString()));

        Person person = this.findById(id);
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setAddress(personDto.getAddress());
        person.setGender(personDto.getGender());

        return repository.save(person);
    }
}
