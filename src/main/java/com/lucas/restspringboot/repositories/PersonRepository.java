package com.lucas.restspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.restspringboot.models.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
