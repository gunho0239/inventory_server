package com.example.inventory_server.service;

import com.example.inventory_server.dto.BulkRequestResponse;
import com.example.inventory_server.entity.PartUnit;
import com.example.inventory_server.repository.PartUnitRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartUnitService {
    private final PartUnitRepository unitRepo;

    public PartUnitService(PartUnitRepository unitRepo) {
        this.unitRepo = unitRepo;
    }

    public List<PartUnit> getAllUnits() {
        return unitRepo.findAll();
    }

    public PartUnit getUnitById(Long id) {
        return unitRepo.findById(id).orElseThrow();
    }

    public PartUnit saveUnit(PartUnit unit) {
        return unitRepo.save(unit);
    }

    public List<PartUnit> saveAllUnits(List<PartUnit> units) {
        List<PartUnit> savedUnits = new ArrayList<>();

        for (PartUnit unit : units) {
            if (!unitRepo.existsByUnit(
                    unit.getUnit()
            )) {
                savedUnits.add(unitRepo.save(unit));
            }
        }

        return savedUnits;
    }

    public void deleteUnit(Long id) {
        unitRepo.deleteById(id);
    }

    public BulkRequestResponse deleteAllUnits(List<Long> ids) {
        BulkRequestResponse result = new BulkRequestResponse();

        for (Long id : ids) {
            try {
                unitRepo.deleteById(id);
                result.increaseSuccussCount();
            } catch (DataIntegrityViolationException e) {
                result.increaseFailedCount();
            }
        }

        return result;
    }
}