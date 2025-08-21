package com.example.inventory_server.controller;

import com.example.inventory_server.dto.BulkRequestResponse;
import com.example.inventory_server.entity.PartUnit;
import com.example.inventory_server.service.PartUnitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/part-units")
public class PartUnitController {
    private final PartUnitService partUnitService;

    public PartUnitController(PartUnitService partUnitService) {
        this.partUnitService = partUnitService;
    }

    @GetMapping("/{id}")
    public PartUnit getUnit(@PathVariable Long id) {
        return partUnitService.getUnitById(id);
    }

    @GetMapping
    public List<PartUnit> getUnits() {
        return partUnitService.getAllUnits();
    }

    @PostMapping
    public PartUnit createUnit(@RequestBody PartUnit unit) {
        return partUnitService.saveUnit(unit);
    }

    @PostMapping("/bulk")
    public List<PartUnit> createUnits(@RequestBody List<PartUnit> units) {
        return partUnitService.saveAllUnits(units);
    }

    @DeleteMapping("/{id}")
    public void deleteUnit(@PathVariable Long id) {
        partUnitService.deleteUnit(id);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<BulkRequestResponse> deleteUnits(@RequestBody List<Long> ids) {
        BulkRequestResponse result = partUnitService.deleteAllUnits(ids);
        return ResponseEntity.ok(result);
    }
}
