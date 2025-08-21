package com.example.inventory_server.repository;

import com.example.inventory_server.entity.PartUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartUnitRepository extends JpaRepository<PartUnit, Long>{
    boolean existsByUnit(String unit);
}