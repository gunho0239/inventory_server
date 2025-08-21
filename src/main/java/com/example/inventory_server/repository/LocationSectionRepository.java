package com.example.inventory_server.repository;

import com.example.inventory_server.entity.LocationSection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationSectionRepository extends JpaRepository<LocationSection, Long> {
    boolean existsBySection(String section);
}
