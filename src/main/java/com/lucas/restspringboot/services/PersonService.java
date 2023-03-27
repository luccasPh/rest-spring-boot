package com.lucas.restspringboot.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.lucas.restspringboot.exceptions.NotFoundBusinessException;
import com.lucas.restspringboot.models.Person;
import com.lucas.restspringboot.models.dto.PersonDTO;

@Service
public class PersonService {

    List<Person> listPersons = new ArrayList<>();

    private final AtomicLong counter = new AtomicLong();

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person findById(Long id) {
        logger.info(String.format("Finding person by id: %s", id));

        return listPersons.stream()
                .filter(person -> id.equals(person.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundBusinessException(
                        String.format("Not found person with id %s not found", id)));
    }

    public List<Person> findAll() {
        logger.info("Finding all persons");

        return listPersons;
    }

    public Person create(PersonDTO personDto) {
        logger.info(String.format("Create a person: %s", personDto.toString()));

        Person person = personDto.toModel();
        person.setId(counter.incrementAndGet());
        listPersons.add(person);

        return person;
    }

    public Person update(Long id, PersonDTO personDto) {
        logger.info(String.format("Update a person id: %s, %s ", id, personDto.toString()));

        Person person = this.findById(id);
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setAddress(personDto.getAddress());
        person.setGender(personDto.getGender());

        return person;
    }
}
