package com.example.inventory_server.repository;

import com.example.inventory_server.entity.PartType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartTypeRepository extends JpaRepository<PartType, Long>{
    boolean existsByType(String type);
}