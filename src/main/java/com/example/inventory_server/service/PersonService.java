package com.example.inventory_server.service;

import com.example.inventory_server.dto.BulkRequestResponse;
import com.example.inventory_server.entity.Person;
import com.example.inventory_server.repository.PersonRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepo;

    public PersonService(PersonRepository personRepo) {
        this.personRepo = personRepo;
    }

    public List<Person> getAllPersons() {
        return personRepo.findAll();
    }

    public Person getPersonById(Long id) {
        return personRepo.findById(id).orElseThrow();
    }

    public Person savePerson(Person person) {
        return personRepo.save(person);
    }

    public List<Person> saveAllPersons(List<Person> persons) {
        List<Person> savedPersons = new ArrayList<>();

        for (Person person : persons) {
            if (!personRepo.existsByName(
                    person.getName()
            )) {
                savedPersons.add(personRepo.save(person));
            }
        }

        return savedPersons;
    }

    public void deletePerson(Long id) {
        personRepo.deleteById(id);
    }

    public BulkRequestResponse deleteAllPersons(List<Long> ids) {
        BulkRequestResponse result = new BulkRequestResponse();

        for (Long id : ids) {
            try {
                personRepo.deleteById(id);
                result.increaseSuccussCount();
            } catch (DataIntegrityViolationException e) {
                result.increaseFailedCount();
            }
        }

        return result;
    }
}