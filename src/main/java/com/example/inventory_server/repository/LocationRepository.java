package com.example.inventory_server.repository;

import com.example.inventory_server.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long>{
    List<Location> findAllByOrderBySection_SectionAscNumberAsc();
    List<Location> findBySection_IdOrderByNumberAsc(Long sectionId);
    boolean existsBySection_IdAndNumber(Long sectionId, Integer number);
}
