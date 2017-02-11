package com.demo.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.app.domain.Person;

@Repository
public interface PersonRepository extends MongoRepository<Person, Integer> {

}
