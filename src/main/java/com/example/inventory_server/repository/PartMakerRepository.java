package com.example.inventory_server.repository;

import com.example.inventory_server.entity.PartMaker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartMakerRepository extends JpaRepository<PartMaker, Long>{
    boolean existsByMaker(String maker);
}
