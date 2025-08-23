package com.example.inventory_server.controller;

import com.example.inventory_server.dto.BulkRequestResponse;
import com.example.inventory_server.entity.Location;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.inventory_server.service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<Location> getLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public Location getLocation(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }

    @GetMapping("/section/{sectionId}")
    public List<Location> getLocationBySectionId(@PathVariable Long sectionId) { return locationService.getLocationBySectionId(sectionId); }

    @GetMapping("/search")
    public List<Location> getLocations(
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Integer number
    ) {
        return locationService.getLocations(sectionId, number);
    }

    @PostMapping("/single")
    public Location createLocation(@RequestBody Location location) {
        return locationService.saveLocation(location);
    }

    @PostMapping("/bulk")
    public List<Location> createLocations(@RequestBody List<Location> locations) {
        return locationService.saveAllLocations(locations);
    }

    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<BulkRequestResponse> deleteLocations(@RequestBody List<Long> ids) {
        BulkRequestResponse result = locationService.deleteAllLocations(ids);
        return ResponseEntity.ok(result);
    }
}