package com.example.inventory_server.controller;

import com.example.inventory_server.dto.BulkRequestResponse;
import com.example.inventory_server.entity.PartMaker;
import com.example.inventory_server.service.PartMakerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/part-makers")
public class PartMakerController {
    private final PartMakerService partMakerService;

    public PartMakerController(PartMakerService partMakerService) {
        this.partMakerService = partMakerService;
    }

    @GetMapping("/{id}")
    public PartMaker getMaker(@PathVariable Long id) {
        return partMakerService.getMakerById(id);
    }

    @GetMapping
    public List<PartMaker> getMakers() {
        return partMakerService.getAllMakers();
    }

    @PostMapping
    public PartMaker createMaker(@RequestBody PartMaker maker) {
        return partMakerService.saveMaker(maker);
    }

    @PostMapping("/bulk")
    public List<PartMaker> createMakers(@RequestBody List<PartMaker> makers) {
        return partMakerService.saveAllMakers(makers);
    }

    @DeleteMapping("/{id}")
    public void deleteMaker(@PathVariable Long id) {
        partMakerService.deleteMaker(id);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<BulkRequestResponse> deleteMakers(@RequestBody List<Long> ids) {
        BulkRequestResponse result = partMakerService.deleteAllMakers(ids);
        return ResponseEntity.ok(result);
    }
}
