package com.example.inventory_server.controller;

import com.example.inventory_server.dto.BulkRequestResponse;
import com.example.inventory_server.entity.PartType;
import com.example.inventory_server.service.PartTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/part-types")
public class PartTypeController {
    private final PartTypeService partTypeService;

    public PartTypeController(PartTypeService partTypeService) {
        this.partTypeService = partTypeService;
    }

    @GetMapping("/{id}")
    public PartType getType(@PathVariable Long id) {
        return partTypeService.getTypeById(id);
    }

    @GetMapping
    public List<PartType> getTypes() {
        return partTypeService.getAllTypes();
    }

    @PostMapping
    public PartType createType(@RequestBody PartType type) {
        return partTypeService.saveType(type);
    }

    @PostMapping("/bulk")
    public List<PartType> createTypes(@RequestBody List<PartType> types) {
        return partTypeService.saveAllTypes(types);
    }

    @DeleteMapping("/{id}")
    public void deleteType(@PathVariable Long id) {
        partTypeService.deleteType(id);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<BulkRequestResponse> deleteTypes(@RequestBody List<Long> ids) {
        BulkRequestResponse result = partTypeService.deleteAllTypes(ids);
        return ResponseEntity.ok(result);
    }
}
