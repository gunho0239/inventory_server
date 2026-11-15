package com.example.inventory_server.controller;

import com.example.inventory_server.dto.BulkRequestResponse;
import com.example.inventory_server.entity.Person;
import com.example.inventory_server.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @GetMapping
    public List<Person> getPersons() {
        return personService.getAllPersons();
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    @PostMapping("/bulk")
    public List<Person> createPersons(@RequestBody List<Person> persons) {
        return personService.saveAllPersons(persons);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<BulkRequestResponse> deletePersons(@RequestBody List<Long> ids) {
        BulkRequestResponse result = personService.deleteAllPersons(ids);
        return ResponseEntity.ok(result);
    }
}
