package com.example.inventory_server.controller;

import com.example.inventory_server.dto.BulkRequestResponse;
import com.example.inventory_server.entity.Part;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.inventory_server.service.PartService;

import java.util.List;

@RestController
@RequestMapping("/parts")
public class PartController {
    private final PartService partService;

    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping
    public List<Part> getParts() {
        return partService.getAllParts();
    }

    @GetMapping("/{id}")
    public Part getPart(@PathVariable Long id) {
        return partService.getPartById(id);
    }

    @GetMapping("/type/{typeId}")
    public List<Part> getPartByTypeId(@PathVariable Long typeId) { return partService.getPartByTypeId(typeId); }

    @GetMapping("/maker/{makerId}")
    public List<Part> getPartByMakerId(@PathVariable Long makerId) { return partService.getPartByMakerId(makerId); }

    @GetMapping("/search")
    public List<Part> getParts(
            @RequestParam(required = false) Long typeId,
            @RequestParam(required = false) Long makerId,
            @RequestParam(required = false) String specification
    ) {
        return partService.getParts(typeId, makerId, specification);
    }


    @PostMapping("/single")
    public Part createPart(@RequestBody Part part) {
        return partService.savePart(part);
    }

    @PostMapping("/bulk")
    public List<Part> createParts(@RequestBody List<Part> parts) {
        return partService.saveAllParts(parts);
    }

    @DeleteMapping("/{id}")
    public void deletePart(@PathVariable Long id) {
        partService.deletePart(id);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<BulkRequestResponse> deleteParts(@RequestBody List<Long> ids) {
        BulkRequestResponse result = partService.deleteAllParts(ids);
        return ResponseEntity.ok(result);
    }
}