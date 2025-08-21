package com.example.inventory_server.repository;

import com.example.inventory_server.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long>{
    List<Part> findAllByOrderByType_TypeAscSpecificationAsc();
    List<Part> findByType_IdOrderBySpecificationAsc(Long typeId);
    List<Part> findByMaker_IdOrderBySpecificationAsc(Long makerId);
    List<Part> findByType_IdAndMaker_IdOrderBySpecificationAsc(Long typeId, Long makerId);
    List<Part> findBySpecificationContainingOrderBySpecificationAsc(String specification);
    List<Part> findByType_IdAndSpecificationContainingOrderBySpecificationAsc(Long typeId, String specification);
    List<Part> findByMaker_IdAndSpecificationContainingOrderBySpecificationAsc(Long makerId, String specification);
    List<Part> findByType_IdAndMaker_IdAndSpecificationContainingOrderBySpecificationAsc(Long typeId, Long makerId, String specification);
    boolean existsByType_IdAndSpecificationAndMaker_IdAndUnit_Id(Long typeId, String specification, Long makerId, Long unitId);
}
