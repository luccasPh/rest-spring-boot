package com.lucas.restspringboot.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.restspringboot.data.dto.v1.PersonDTO;
import com.lucas.restspringboot.data.dto.v2.PersonDTOV2;
import com.lucas.restspringboot.exceptions.NotFoundBusinessException;
import com.lucas.restspringboot.models.Person;
import com.lucas.restspringboot.repositories.PersonRepository;
import com.lucas.restspringboot.shared.mapper.ObjectMapper;

@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public PersonDTO findById(Long id) {
        logger.info(String.format("Finding person by id: %s", id));
        Person person = repository.findById(id)
                .orElseThrow(() -> new NotFoundBusinessException(
                        String.format("Not found person with id: %s", id)));

        return ObjectMapper.parseObject(person, PersonDTO.class);
    }

    public List<PersonDTO> findAll() {
        logger.info("Finding all persons");

        return ObjectMapper.parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO create(PersonDTO personDto) {
        logger.info(String.format("Create a person: %s", personDto.toString()));

        Person person = ObjectMapper.parseObject(personDto, Person.class);

        return ObjectMapper.parseObject(repository.save(person), PersonDTO.class);
    }

    public PersonDTOV2 createV2(PersonDTOV2 personDto) {
        logger.info(String.format("Create a person: %s", personDto.toString()));

        Person person = ObjectMapper.parseObject(personDto, Person.class);

        return ObjectMapper.parseObject(repository.save(person), PersonDTOV2.class);
    }

    public PersonDTO update(Long id, PersonDTO personDto) {
        logger.info(String.format("Update a person id: %s, %s ", id, personDto.toString()));

        Person person = repository.findById(id)
                .orElseThrow(() -> new NotFoundBusinessException(
                        String.format("Not found person with id: %s", id)));
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setAddress(personDto.getAddress());
        person.setGender(personDto.getGender());

        return ObjectMapper.parseObject(repository.save(person), PersonDTO.class);
    }
}
