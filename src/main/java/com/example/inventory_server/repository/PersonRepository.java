package com.example.inventory_server.repository;

import com.example.inventory_server.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long>{
    boolean existsByName(String name);
}
