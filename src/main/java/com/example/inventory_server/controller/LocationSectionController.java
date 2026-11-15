package com.example.inventory_server.controller;

import com.example.inventory_server.dto.BulkRequestResponse;
import com.example.inventory_server.entity.LocationSection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.inventory_server.service.LocationSectionService;

import java.util.List;

@RestController
@RequestMapping("/api/location-sections")
public class LocationSectionController {
    private final LocationSectionService locationSectionService;

    public LocationSectionController(LocationSectionService locationSectionService) {
        this.locationSectionService = locationSectionService;
    }

    @GetMapping
    public List<LocationSection> getSections() {
        return locationSectionService.getAllSections();
    }

    @GetMapping("/{id}")
    public LocationSection getSection(@PathVariable Long id) {
        return locationSectionService.getSectionById(id);
    }

    @PostMapping
    public LocationSection createSection(@RequestBody LocationSection section) {
        return locationSectionService.saveSection(section);
    }

    @PostMapping("/bulk")
    public List<LocationSection> createSections(@RequestBody List<LocationSection> sections) {
        return locationSectionService.saveAllSections(sections);
    }

    @DeleteMapping("/{id}")
    public void deleteSection(@PathVariable Long id) {
        locationSectionService.deleteSection(id);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<BulkRequestResponse> deleteSections(@RequestBody List<Long> ids) {
        BulkRequestResponse result = locationSectionService.deleteAllSections(ids);
        return ResponseEntity.ok(result);
    }
}
